package com.excilys.computerdatabase.persistence.impl;

import com.excilys.computerdatabase.common.LogOperationType;
import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.domain.Log;
import com.excilys.computerdatabase.persistence.LogDao;
import com.excilys.computerdatabase.persistence.factory.DaoFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created with IntelliJ IDEA.
 * User: lortola
 * Date: 31/12/13
 * Time: 10:57
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class LogDaoImpl implements LogDao {

    private static Logger logger = LoggerFactory.getLogger(ComputerDaoImpl.class);

    @Autowired
    private DaoFactory df;

    @Override
    public Log create(Log log) {
        logger.debug("Entering create with object " + log);
        String sql = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        Connection conn = df.getConn();

        try {
            // Get a connection from the DaoManager
            logger.debug("Connecting to database...");

            // Execute query
            logger.debug("Creating statement...");

            sql = "INSERT INTO log(operation_type, operation_date, description) VALUES(?,?,?);";
            stmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, log.getOperationType().toString());
            if(log.getOperationDate() != null) {
                stmt.setTimestamp(2,new Timestamp(log.getOperationDate().getTime().getTime()));
            }
            else
                stmt.setNull(2, Types.TIMESTAMP);
            stmt.setString(3, log.getDescription());

            stmt.executeUpdate();
            rs = stmt.getGeneratedKeys();
            rs.first();
            log.setId(rs.getLong(1));
            rs.close();

        } catch (SQLException se) {
            logger.warn("Error in SQL query:" + se.getMessage());
            df.notifyTransactionError();
        } finally {
            // Clean-up environment
            try {
                if(conn != null && conn.getAutoCommit())
                    df.closeConn();
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
                logger.warn("Cannot close JDBC related objects:" + e.getMessage());
                e.printStackTrace();
            }
        }

        logger.debug("leaving create");
        return log;
    }
}
