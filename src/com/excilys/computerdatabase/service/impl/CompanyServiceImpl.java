package com.excilys.computerdatabase.service.impl;

import com.excilys.computerdatabase.common.Page;
import com.excilys.computerdatabase.domain.Company;
import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.persistence.CompanyDao;
import com.excilys.computerdatabase.persistence.factory.DaoFactory;
import com.excilys.computerdatabase.service.CompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Project: computer-database
 * Package: com.excilys.computerdatabase.service.impl
 * User: lortola
 * Date: 15/11/13
 * Description: N/A
 */
public class CompanyServiceImpl implements CompanyService {

    private CompanyDao companyDao = DaoFactory.INSTANCE.getCompanyDao();

    private static Logger logger = LoggerFactory.getLogger(CompanyServiceImpl.class);

    @Override
    public List<Company> retrieveAll() {
        return companyDao.retrieveAll();
    }

    @Override
    public Company retrieve(Long companyId) {
        return companyDao.retrieve(companyId);
    }
}
