package com.excilys.computerdatabase.service.impl;

import com.excilys.computerdatabase.domain.Company;
import com.excilys.computerdatabase.persistence.CompanyDao;
import com.excilys.computerdatabase.persistence.factory.DaoFactory;
import com.excilys.computerdatabase.service.CompanyService;

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

    @Override
    public List<Company> retrieveAll() {
        return companyDao.retrieveAll();
    }

    @Override
    public Company retrieve(Long companyId) {
        return companyDao.retrieve(companyId);
    }
}
