package com.excilys.computerdatabase.persistence.factory;

import com.excilys.computerdatabase.persistence.CompanyDao;
import com.excilys.computerdatabase.persistence.ComputerDao;
import com.excilys.computerdatabase.persistence.impl.CompanyDaoImpl;
import com.excilys.computerdatabase.persistence.impl.ComputerDaoImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
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


    /*
     * Private constructor
     */
    private DaoFactory() {

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
            logger.error("Couldn't load jdbc Driver:" + e.getMessage());
            error = true;
        }
        companyDao = new CompanyDaoImpl();
        computerDao = new ComputerDaoImpl();

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

    public Connection getConn() {

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(prop.getProperty("dburl"), prop.getProperty("dbuser"), prop.getProperty("dbpassword"));
        } catch (SQLException e) {
            logger.warn("Couldn't get JDBC Connection:" + e.getMessage());
        }
        return conn;
    }
}
