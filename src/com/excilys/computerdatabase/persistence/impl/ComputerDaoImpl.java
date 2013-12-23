package com.excilys.computerdatabase.persistence.impl;

import com.excilys.computerdatabase.common.Page;
import com.excilys.computerdatabase.domain.Company;
import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.persistence.ComputerDao;
import com.excilys.computerdatabase.persistence.factory.DaoFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Project: computer-database
 * Package: com.excilys.computerdatabase.persistence.impl
 * Computer: lortola
 * Date: 15/11/13
 * Description: N/A
 */
public class ComputerDaoImpl implements ComputerDao {

    private static Logger logger = LoggerFactory.getLogger(ComputerDaoImpl.class);
    private static DateFormat dateFormat = new SimpleDateFormat();

    @Override
    public void create(Computer computer) {
        logger.debug("Entering create with object " + computer);
        String sql = null;
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // Get a connection from the DaoManager
            logger.debug("Connecting to database...");

            conn = DaoFactory.INSTANCE.getConn();

            // Execute query
            logger.debug("Creating statement...");

            sql = "INSERT INTO computer(name, introduced, discontinued, company_id) VALUES(?,?,?,?);";
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, computer.getName());
            if(computer.getIntroduced() != null) {
                stmt.setDate(2,new java.sql.Date(computer.getIntroduced().getTime().getTime()));
            }
            else
                stmt.setNull(2,Types.TIMESTAMP);
            if(computer.getDiscontinued() != null)
                stmt.setDate(3,new java.sql.Date(computer.getDiscontinued().getTime().getTime()));
            else
                stmt.setNull(3,Types.TIMESTAMP);
            if(computer.getCompany() != null) {
                stmt.setLong(4,computer.getCompany().getId());
            }
            else
                stmt.setNull(4,Types.BIGINT);

            stmt.executeUpdate();

        } catch (SQLException se) {
            logger.warn("Error in SQL query:" + se.getMessage());
        } finally {
            closeObjects(conn,stmt);
        }

        logger.debug("leaving create");

    }

    @Override
    public Page<Computer> retrieveAll() {
        logger.debug("Entering retrieveAll");

        List<Computer> computers = new ArrayList<Computer>();
        int count = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Get a connection from the DaoManager
            logger.debug("Connecting to database...");
            conn = DaoFactory.INSTANCE.getConn();

            // Execute query
            logger.debug("Creating statement...");

            String sql;
            sql = "SELECT computer.id, computer.name, introduced, discontinued, company.id, company.name FROM computer LEFT JOIN company ON computer.company_id = company.id; ";

            stmt = conn.prepareStatement(sql);

            rs = stmt.executeQuery(sql);
            // Extract data from result set
            while (rs.next()) {
                count++;
                //Create computer result using builder pattern
                Computer computer = Computer.builder().id(rs.getLong("computer.id"))
                        .name(rs.getString("computer.name"))
                        .introduced(rs.getDate("introduced"))
                        .discontinued(rs.getDate("discontinued"))
                        .company(new Company(rs.getLong("company.id"), rs.getString("company.name")))
                        .build();

                computers.add(computer);
            }


        } catch (SQLException se) {
            logger.warn("Error in SQL query:" + se.getMessage());
        } finally {
            closeObjects(conn,stmt,rs);
        }

        logger.debug("Found " + computers.size() + " elements");

        Page<Computer> computerPage = new Page<Computer>();
        computerPage.setItems(computers);
        computerPage.setRecordCount(count);
        return computerPage;
    }

    @Override
    public Computer retrieve(Long computerId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Computer computer = null;
        try {
            // Get a connection from the DaoManager
            logger.debug("Connecting to database...");
            conn = DaoFactory.INSTANCE.getConn();

            // Execute query
            logger.debug("Creating statement...");

            String sql = "SELECT computer.id, computer.name, introduced, discontinued, company.id, company.name FROM computer LEFT JOIN company on computer.company_id = company.id WHERE computer.id=?";

            stmt = conn.prepareStatement(sql);

            stmt.setLong(1, computerId);

            rs = stmt.executeQuery();

            // Extract data from result set
            while(rs.next()) {
                //Create company result
                computer = Computer.builder()
                        .id(rs.getLong("id"))
                        .name(rs.getString("computer.name"))
                        .introduced(rs.getDate("introduced"))
                        .discontinued(rs.getDate("discontinued"))
                        .company(new Company(rs.getLong("company.id"), rs.getString("company.name")))
                        .build();
                break;
            }

        } catch (SQLException se) {
            logger.warn("Error in SQL query:" + se.getMessage());
        } finally {
            closeObjects(conn,stmt,rs);
        }
        return computer;
    }

    @Override
    public boolean update(Computer computer) {
        boolean result = false;
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // Get a connection from the DaoManager
            logger.debug("Connecting to database...");
            conn = DaoFactory.INSTANCE.getConn();

            // Execute query
            logger.debug("Creating statement...");

            String sql = "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?";

            stmt = conn.prepareStatement(sql);

            stmt.setString(1, computer.getName());
            if(computer.getIntroduced() != null)
                stmt.setDate(2, new java.sql.Date(computer.getIntroduced().getTime().getTime()));
            else
                stmt.setNull(2,Types.TIMESTAMP);
            if(computer.getDiscontinued() != null)
                stmt.setDate(3, new java.sql.Date(computer.getDiscontinued().getTime().getTime()));
            else
                stmt.setNull(3, Types.TIMESTAMP);
            if(computer.getCompany() != null)
                stmt.setLong(4, computer.getCompany().getId());
            else
                stmt.setNull(4, Types.BIGINT);
            stmt.setLong(5, computer.getId());

            if(stmt.executeUpdate() == 1)
                result=true;

        } catch (SQLException se) {
            logger.warn("Error in SQL query:" + se.getMessage());
        } finally {
            closeObjects(conn,stmt);
        }

        if(!result)
            logger.warn("Computer update failed");

        return result;
    }

    @Override
    public boolean delete(List<Long> computerIds) {
        boolean result = false;
        Connection conn = null;
        PreparedStatement stmt = null;
        String computers = "";

        if(computerIds == null || computerIds.isEmpty()) {
            logger.debug("Nothing to delete");
            return false;
        }


        try {
            // Get a connection from the DaoManager
            logger.debug("Connecting to database...");
            conn = DaoFactory.INSTANCE.getConn();

            // Execute query
            logger.debug("Creating statement...");

            StringBuilder sql = new StringBuilder();

            if(computerIds.size() == 1)
                sql.append("DELETE FROM computer WHERE id = ?");
            else {

                sql.append("DELETE FROM computer WHERE id IN (");
                for(int i=0;i<computerIds.size();i++) {
                    sql.append("?");
                    if(i < computerIds.size()-1)
                        sql.append(", ");
                }
                sql.append(")");
            }

            stmt = conn.prepareStatement(sql.toString());

            for(int i=0;i<computerIds.size();i++) {
                stmt.setLong(i+1, computerIds.get(i));
            }

            if(stmt.executeUpdate() > 0)
                result=true;

        } catch (SQLException se) {
            logger.warn("Error in SQL query:" + se.getMessage());
        } finally {
            closeObjects(conn,stmt);
        }

        if(!result)
            logger.warn("Computer deletion failed");

        return result;
    }

	private void closeObjects(Connection conn, Statement stmt) {
        closeObjects(conn,stmt,null);
    }

    private void closeObjects(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (conn != null)
                conn.close();
            if (stmt != null)
                stmt.close();
            if (rs != null)
                rs.close();
        } catch (SQLException e) {
            logger.warn("Cannot close JDBC related objects:" + e.getMessage());
            e.printStackTrace();
        }
    }
}
