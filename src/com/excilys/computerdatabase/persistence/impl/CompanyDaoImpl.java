package com.excilys.computerdatabase.persistence.impl;

import com.excilys.computerdatabase.domain.Company;
import com.excilys.computerdatabase.persistence.CompanyDao;
import com.excilys.computerdatabase.persistence.factory.DaoFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Project: computer-database
 * Package: com.excilys.computerdatabase.persistence.impl
 * User: lortola
 * Date: 15/11/13
 * Description: N/A
 */
public class CompanyDaoImpl implements CompanyDao {

    private static Logger logger = LoggerFactory.getLogger(CompanyDaoImpl.class);

    @Override
    public List<Company> retrieveAll() {
        logger.debug("Entering retrieveAll");
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Company> companies = new ArrayList<Company>();

        try {
            // Get a connection from the DaoManager
            logger.debug("Connecting to database...");
            conn = DaoFactory.INSTANCE.getConn();

            // Execute query
            logger.debug("Creating statement...");

            String sql;
            sql = "SELECT id, name FROM company;";

            stmt = conn.prepareStatement(sql);

            rs = stmt.executeQuery(sql);

            // Extract data from result set
            while (rs.next()) {
                //Create company result
                companies.add(new Company(rs.getLong("id"),rs.getString("name")));
            }

        } catch (SQLException se) {
            logger.warn("Error in SQL query:" + se.getMessage());
        } finally {
            closeObjects(conn,stmt,rs);
        }

        logger.debug(new StringBuilder("Found ").append(companies.size()).append(" elements").toString());
        logger.debug("Leaving retrieveAll");

        return companies;
    }

    @Override
    public Company retrieve(Long companyId) {
        logger.debug("Entering retrieve");
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Company company = null;
        try {
            //Get a connection from the DaoManager
            logger.debug("Connecting to database...");
            conn = DaoFactory.INSTANCE.getConn();

            //Execute query
            logger.debug("Creating statement...");

            String sql = "SELECT id, name FROM company WHERE id=?";

            stmt = conn.prepareStatement(sql);

            stmt.setLong(1, companyId);

            rs = stmt.executeQuery();

            //Extract data from result set
            while(rs.next()) {
                //Create company result
                company = new Company(rs.getLong("id"),rs.getString("name"));
                break;
            }

        } catch (SQLException se) {
            logger.warn("Error in SQL query:" + se.getMessage());
        } finally {
            closeObjects(conn,stmt,rs);
        }
        logger.debug("Leaving retrieve");
        return company;
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
