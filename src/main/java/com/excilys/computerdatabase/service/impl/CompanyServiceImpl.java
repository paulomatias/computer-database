package com.excilys.computerdatabase.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.computerdatabase.domain.Company;
import com.excilys.computerdatabase.persistence.CompanyDao;
import com.excilys.computerdatabase.service.CompanyService;
import org.springframework.transaction.annotation.Transactional;

/**
 * Project: computer-database
 * Package: com.excilys.computerdatabase.service.impl
 * User: lortola
 * Date: 15/11/13
 * Description: N/A
 */
@Service
@Transactional(readOnly = true)
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyDao companyDao;

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
