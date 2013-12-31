package com.excilys.computerdatabase.persistence.factory;

import com.excilys.computerdatabase.persistence.CompanyDao;
import com.excilys.computerdatabase.persistence.ComputerDao;
import com.excilys.computerdatabase.persistence.LogDao;
import com.excilys.computerdatabase.persistence.impl.CompanyDaoImpl;
import com.excilys.computerdatabase.persistence.impl.ComputerDaoImpl;
import com.excilys.computerdatabase.persistence.impl.LogDaoImpl;
import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Project: computer-database
 * Package: com.excilys.computerdatabase.persistence.factory
 * User: lortola
 * Date: 15/11/13
 * Description: N/A
 */
public enum DaoFactory {
    INSTANCE;

    /*
     * Attributes
     */

    private Logger logger = LoggerFactory.getLogger(DaoFactory.class);

    //Getting DB Connection properties from secured properties file
    Properties prop;

    private CompanyDao companyDao;
    private ComputerDao computerDao;
    private LogDao logDao;
    private BoneCP connectionPool;
    private ThreadLocal<Connection> connectionThreadLocal;
    private ThreadLocal<Boolean> transactionError;

    /*
     * Private constructor
     */
    private DaoFactory() {
        logger.debug("Loading DaoFactory");
        boolean error = false;

        prop = new Properties();
        try {
            prop.load(DaoFactory.class.getClassLoader().getResourceAsStream("/dbconf.properties"));
        } catch (Exception e) {
            logger.error("Couldn't load db configuration properties file: " + e.getMessage());
            error = true;
        }
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            logger.error("Couldn't load jdbc Driver: " + e.getMessage());
            error = true;
        }
        //Creating bone CP config
        BoneCPConfig config = new BoneCPConfig();
        config.setJdbcUrl(prop.getProperty("dburl"));
        config.setUsername(prop.getProperty("dbuser"));
        config.setPassword(prop.getProperty("dbpassword"));
        config.setMinConnectionsPerPartition(5);
        config.setMaxConnectionsPerPartition(10);
        config.setPartitionCount(1);
        //Instanciating connection pool
        try {
            connectionPool = new BoneCP(config);
        } catch (SQLException e) {
            logger.error("Couldn't instanciate connection pool: " + e.getMessage());
            error = true;
        }
        companyDao = new CompanyDaoImpl();
        computerDao = new ComputerDaoImpl();
        logDao = new LogDaoImpl();

        connectionThreadLocal = new ThreadLocal<Connection>();
        transactionError = new ThreadLocal<Boolean>();

        if(error)
            logger.error("DaoFactory created with errors");
        else
            logger.debug("DaoFactory successfully created");
    }

    /*
     * Getters
     */

    public CompanyDao getCompanyDao() {
        return companyDao;
    }

    public ComputerDao getComputerDao() {
        return computerDao;
    }

    public LogDao getLogDao() {
        return logDao;
    }

    public Connection getConn() {

        if(connectionThreadLocal.get() == null)
            try {
                connectionThreadLocal.set(connectionPool.getConnection());
            } catch (SQLException e) {
                logger.warn("Couldn't get JDBC Connection:" + e.getMessage());
            }
        return connectionThreadLocal.get();
    }

    public void startTransaction() {
        try {
            getConn().setAutoCommit(false);
            if(transactionError.get() == null)
                transactionError.set(false);
        } catch (SQLException e) {
            logger.warn("Error while starting transaction:"+e.getMessage());
            e.printStackTrace();
        }

    }

    public void commitTransaction() {
        try {
            //If we have an error during the transaction process
            if(transactionError.get() != null && transactionError.get())
                rollbackTransaction();
            else
                getConn().commit();
            getConn().setAutoCommit(true);
        } catch (SQLException e) {
            logger.warn("Error while commiting transaction:"+e.getMessage());
            rollbackTransaction();
            e.printStackTrace();
        }
    }

    public void notifyTransactionError() {
        if(transactionError.get() != null) {
            logger.warn("Transaction error detected. Will rollback");
            transactionError.set(true);
        }
    }

    public void rollbackTransaction() {
        try {
            getConn().rollback();
            getConn().setAutoCommit(true);
        } catch (SQLException e) {
            logger.warn("Error during transaction rollback:"+e.getMessage());
            e.printStackTrace();
        }
    }

    public void closeConn() {
        if(connectionThreadLocal.get() != null)
            try {
                connectionThreadLocal.get().close();
            } catch (SQLException e) {
                logger.warn("Cannot close JDBC related objects:" + e.getMessage());
            }
        connectionThreadLocal.remove();
    }
}
