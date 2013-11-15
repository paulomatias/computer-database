package com.excilys.computerdatabase.service.factory;

import com.excilys.computerdatabase.service.CompanyService;
import com.excilys.computerdatabase.service.ComputerService;
import com.excilys.computerdatabase.service.impl.CompanyServiceImpl;
import com.excilys.computerdatabase.service.impl.ComputerServiceImpl;

/**
 * Project: computer-database
 * Package: com.excilys.computerdatabase.service.factory
 * User: lortola
 * Date: 15/11/13
 * Description: N/A
 */
public enum ServiceFactory {
    INSTANCE;


    /*
     * Attributes
     */

    private CompanyService companyService;
    private ComputerService computerService;

    /*
     * Private constructor
     */
    private ServiceFactory() {
        companyService = new CompanyServiceImpl();
        computerService = new ComputerServiceImpl();
    }

    /*
     * Getters
     */

    public CompanyService getCompanyService() {
        return companyService;
    }

    public ComputerService getComputerService() {
        return computerService;
    }

}
