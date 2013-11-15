package com.excilys.computerdatabase.persistence.factory;

import com.excilys.computerdatabase.persistence.CompanyDao;
import com.excilys.computerdatabase.persistence.ComputerDao;
import com.excilys.computerdatabase.persistence.impl.CompanyDaoImpl;
import com.excilys.computerdatabase.persistence.impl.ComputerDaoImpl;

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

    private CompanyDao companyDao;
    private ComputerDao computerDao;


    /*
     * Private constructor
     */
    private DaoFactory() {
        companyDao = new CompanyDaoImpl();
        computerDao = new ComputerDaoImpl();
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
}
