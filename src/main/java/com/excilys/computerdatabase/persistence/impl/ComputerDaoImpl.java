package com.excilys.computerdatabase.persistence.impl;

import com.excilys.computerdatabase.common.Page;
import com.excilys.computerdatabase.domain.Company;
import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.persistence.ComputerDao;
import com.excilys.computerdatabase.persistence.factory.DaoFactory;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Project: computer-database
 * Package: com.excilys.computerdatabase.persistence.impl
 * Computer: lortola
 * Date: 15/11/13
 * Description: N/A
 */
@Repository
public class ComputerDaoImpl implements ComputerDao {

    private static Logger logger = LoggerFactory.getLogger(ComputerDaoImpl.class);

    private static final String COMPUTER_ASC = "computer.name ASC ";
    private static final String INTRODUCED_ASC = "CASE WHEN (computer.introduced IS NULL OR computer.introduced = '0000-00-00 00:00:00') THEN 1 ELSE 0 END ASC, computer.introduced ASC ";
    private static final String DISCONTINUED_ASC = "CASE WHEN (computer.discontinued IS NULL OR computer.discontinued = '0000-00-00 00:00:00') THEN 1 ELSE 0 END ASC, computer.discontinued ASC ";
    private static final String COMPANY_ASC = "CASE WHEN company.name IS NULL THEN 1 ELSE 0 END ASC, company.name ASC ";
    private static final String COMPUTER_DESC = "computer.name DESC ";
    private static final String INTRODUCED_DESC = "CASE WHEN (computer.introduced IS NULL OR computer.introduced = '0000-00-00 00:00:00') THEN 1 ELSE 0 END ASC, computer.introduced DESC ";
    private static final String DISCONTINUED_DESC = "CASE WHEN (computer.discontinued IS NULL OR computer.discontinued = '0000-00-00 00:00:00') THEN 1 ELSE 0 END ASC, computer.discontinued DESC ";
    private static final String COMPANY_DESC = "CASE WHEN company.name IS NULL THEN 1 ELSE 0 END ASC, company.name DESC ";

    @Autowired
    private DaoFactory df;

    @Override
    public Computer create(Computer computer) {
        logger.debug("Entering create with object " + computer);
        String sql = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        Connection conn = df.getConn();

        try {

            // Execute query
            logger.debug("Creating statement...");

            sql = "INSERT INTO computer(name, introduced, discontinued, company_id) VALUES(?,?,?,?);";
            stmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, computer.getName());
            if(computer.getIntroduced() != null)
                stmt.setDate(2,new java.sql.Date(computer.getIntroduced().getMillis()));
            else
                stmt.setNull(2,Types.TIMESTAMP);
            if(computer.getDiscontinued() != null)
                stmt.setDate(3,new java.sql.Date(computer.getDiscontinued().getMillis()));
            else
                stmt.setNull(3,Types.TIMESTAMP);
            if(computer.getCompany() != null)
                stmt.setLong(4,computer.getCompany().getId());
            else
                stmt.setNull(4,Types.BIGINT);

            stmt.executeUpdate();
            rs = stmt.getGeneratedKeys();
            rs.first();
            computer.setId(rs.getLong(1));
            rs.close();

        } catch (SQLException se) {
            logger.warn("Error in SQL query:" + se.getMessage());
            df.notifyTransactionError();
        } finally {
            closeObjects(conn,stmt,rs);
        }

        logger.debug("leaving create");
        return computer;
    }

    @Override
    public Page<Computer> retrieveAll() {
        logger.debug("Entering retrieveAll");
        return retrievePage(0,0,null,0);
    }

    @Override
    public Page<Computer> retrievePage(int offset, int limit, String searchString, int sort) {
        logger.debug(new StringBuilder("Entering retrievePage with offset ").append(offset).append(" limit ").append(limit).append(" searchString ").append(searchString).append(" sort ").append(sort).toString());

        Connection conn = df.getConn();

        List<Computer> computers = new ArrayList<Computer>();
        int count = 0;
        int totalCount = 0;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        Page<Computer> computerPage = new Page<Computer>();

        computerPage.setSearchString(searchString);
        computerPage.setSort(sort);

        if(searchString != null)
            searchString = new StringBuilder().append("%").append(searchString).append("%").toString();

        try {
            // Execute query
            logger.debug("Creating statement...");

            StringBuilder sql = new StringBuilder();

            StringBuilder sql2 = new StringBuilder("SELECT count(*) AS count FROM computer LEFT JOIN company ON computer.company_id = company.id ");
            if(searchString != null)
                sql2.append("WHERE computer.name LIKE ? OR company.name LIKE ? ");

            sql.append("SELECT computer.id, computer.name, introduced, discontinued, company.id, company.name FROM computer LEFT JOIN company ON computer.company_id = company.id ");
            if(searchString != null)
                sql.append("WHERE computer.name LIKE ? OR company.name LIKE ? ");

            sql.append("ORDER BY ");


            //Sort computer
            switch(sort) {
                case 0:
                    sql.append(COMPUTER_ASC).append(", ").append(INTRODUCED_ASC).append(", ").append(DISCONTINUED_ASC).append(", ").append(COMPANY_ASC);
                    break;
                case 1:
                    sql.append(COMPUTER_DESC).append(", ").append(INTRODUCED_ASC).append(", ").append(DISCONTINUED_ASC).append(", ").append(COMPANY_ASC);
                    break;
                case 2:
                    sql.append(INTRODUCED_ASC).append(", ").append(COMPUTER_ASC).append(", ").append(DISCONTINUED_ASC).append(", ").append(COMPANY_ASC);
                    break;
                case 3:
                    sql.append(INTRODUCED_DESC).append(", ").append(COMPUTER_ASC).append(", ").append(DISCONTINUED_ASC).append(", ").append(COMPANY_ASC);
                    break;
                case 4:
                    sql.append(DISCONTINUED_ASC).append(", ").append(COMPUTER_ASC).append(", ").append(INTRODUCED_ASC).append(", ").append(COMPANY_ASC);
                    break;
                case 5:
                    sql.append(DISCONTINUED_DESC).append(", ").append(COMPUTER_ASC).append(", ").append(INTRODUCED_ASC).append(", ").append(COMPANY_ASC);
                    break;
                case 6:
                    sql.append(COMPANY_ASC).append(", ").append(COMPUTER_ASC).append(", ").append(INTRODUCED_ASC).append(", ").append(DISCONTINUED_ASC);
                    break;
                case 7:
                    sql.append(COMPANY_DESC).append(", ").append(COMPUTER_ASC).append(", ").append(INTRODUCED_ASC).append(", ").append(DISCONTINUED_ASC);
                    break;
            }

            if(limit > 0)
                sql.append("LIMIT ").append(limit).append(" ");
            if(offset > 0)
                sql.append("OFFSET ").append(offset);

            stmt = conn.prepareStatement(sql.toString());
            if(searchString != null) {
                stmt.setString(1,searchString);
                stmt.setString(2,searchString);
            }
            rs = stmt.executeQuery();
            // Extract data from result set
            while (rs.next()) {
                count++;
                //Create computer result using builder pattern
                Computer computer = Computer.builder().id(rs.getLong("computer.id"))
                        .name(rs.getString("computer.name"))
                        .introduced(rs.getDate("introduced") == null ? null : new DateTime(rs.getDate("introduced")))
                        .discontinued(rs.getDate("discontinued") == null ? null : new DateTime(rs.getDate("discontinued")))
                        .company(new Company(rs.getLong("company.id"), rs.getString("company.name")))
                        .build();

                computers.add(computer);
            }

            rs.close();
            stmt.close();

            stmt = conn.prepareStatement(sql2.toString());
            if(searchString != null) {
                stmt.setString(1,searchString);
                stmt.setString(2,searchString);
            }

            rs = stmt.executeQuery();

            rs.first();

            totalCount = rs.getInt("count");


        } catch (SQLException se) {
            logger.warn("Error in SQL query:" + se.getMessage());
            df.notifyTransactionError();
        } finally {
            closeObjects(conn,stmt,rs);
        }

        logger.debug("Found " + computers.size() + " elements");

        computerPage.setItems(computers);
        computerPage.setRecordCount(count);

        if(limit > 0) {
            computerPage.setLimit(limit);
            computerPage.setCurrentPage((int) Math.floor(offset / limit) + 1);
            if(totalCount % limit == 0)
                computerPage.setPageCount((int) Math.floor(totalCount / limit));
            else
                computerPage.setPageCount((int) Math.floor(totalCount / limit) + 1);
            computerPage.setTotalCount(totalCount);
        }

        logger.debug("Leaving retrievePage");
        return computerPage;
    }

    @Override
    public Computer retrieve(Long computerId) {
        logger.debug("Entering retrieve");

        PreparedStatement stmt = null;
        ResultSet rs = null;
        Computer computer = null;

        Connection conn = df.getConn();

        try {
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
                        .introduced(rs.getDate("introduced") == null ? null : new DateTime(rs.getDate("introduced")))
                        .discontinued(rs.getDate("discontinued") == null ? null : new DateTime(rs.getDate("discontinued")))
                        .company(new Company(rs.getLong("company.id"), rs.getString("company.name")))
                        .build();
                break;
            }

        } catch (SQLException se) {
            logger.warn("Error in SQL query:" + se.getMessage());
            df.notifyTransactionError();
        } finally {
            closeObjects(conn,stmt,rs);
        }

        logger.debug("Leaving retrieve");
        return computer;
    }

    @Override
    public boolean update(Computer computer) {
        logger.debug("Entering update");

        boolean result = false;
        PreparedStatement stmt = null;

        Connection conn = df.getConn();

        try {
            // Execute query
            logger.debug("Creating statement...");

            String sql = "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?";

            stmt = conn.prepareStatement(sql);

            stmt.setString(1, computer.getName());
            if(computer.getIntroduced() != null)
                stmt.setDate(2, new java.sql.Date(computer.getIntroduced().getMillis()));
            else
                stmt.setNull(2,Types.TIMESTAMP);
            if(computer.getDiscontinued() != null)
                stmt.setDate(3, new java.sql.Date(computer.getDiscontinued().getMillis()));
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
            df.notifyTransactionError();
        } finally {
            closeObjects(conn,stmt);
        }

        if(!result)
            logger.warn("Computer update failed");

        logger.debug("Leaving update");
        return result;
    }

    @Override
    public boolean delete(List<Long> computerIds) {
        logger.debug("Entering delete");

        boolean result = false;
        PreparedStatement stmt = null;
        String computers = "";

        Connection conn = df.getConn();

        if(computerIds == null || computerIds.isEmpty()) {
            logger.debug("Nothing to delete");
            return false;
        }


        try {
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
            df.notifyTransactionError();
        } finally {
            closeObjects(conn,stmt);
        }

        if(!result)
            logger.warn("Computer deletion failed");
        logger.debug("Leaving delete");
        return result;
    }

		private void closeObjects(Connection conn, Statement stmt) {
        closeObjects(conn,stmt,null);
    }

    private void closeObjects(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if(conn != null && conn.getAutoCommit())
                df.closeConn();
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
