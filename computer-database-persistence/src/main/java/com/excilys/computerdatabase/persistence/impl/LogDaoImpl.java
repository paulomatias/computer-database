package com.excilys.computerdatabase.persistence.impl;

import com.excilys.computerdatabase.domain.Log;
import com.excilys.computerdatabase.persistence.LogDao;
import com.jolbox.bonecp.BoneCPDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.CleanupFailureDataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import java.sql.*;

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
    @Qualifier(value = "computerDatabaseDataSource")
    private BoneCPDataSource ds;

    @Override
    public Log create(Log log) {
        logger.debug("Entering create with object " + log);
        String sql = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        // Get a connection from the DaoManager
        logger.debug("Connecting to database...");
        Connection conn = DataSourceUtils.getConnection(ds);

        try {
            logger.debug("Creating statement...");

            sql = "INSERT INTO log(operation_type, operation_date, description) VALUES(?,?,?);";
            stmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, log.getOperationType().toString());
            if(log.getOperationDate() != null) {
                stmt.setTimestamp(2,new Timestamp(log.getOperationDate().getMillis()));
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
            throw new DataAccessResourceFailureException("Error in SQL query:" + se.getMessage());
        } finally {
            // Clean-up environment
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
                logger.warn("Cannot close JDBC related objects:" + e.getMessage());
                e.printStackTrace();
                throw new CleanupFailureDataAccessException("Cannot close JDBC related objects",e);
            }
        }

        logger.debug("leaving create");
        return log;
    }
}
