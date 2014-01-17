package com.excilys.computerdatabase.persistence.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.domain.Company;
import com.excilys.computerdatabase.persistence.CompanyDao;

/**
 * Project: computer-database
 * Package: com.excilys.computerdatabase.persistence.impl
 * User: lortola
 * Date: 15/11/13
 * Description: N/A
 */
@Repository
public class CompanyDaoImpl implements CompanyDao {

    private static Logger logger = LoggerFactory.getLogger(CompanyDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;
 
    @SuppressWarnings("unchecked")
	@Override
    public List<Company> retrieveAll() {
        logger.debug("Entering retrieveAll");
        List<Company> companies = null;

        String jpql = "SELECT c FROM Company c";

        companies = sessionFactory.getCurrentSession().createQuery(jpql).list();

        logger.debug(new StringBuilder("Found ").append(companies.size()).append(" elements").toString());

        logger.debug("Leaving retrieveAll");

        return companies;
    }

    @Override
    public Company retrieve(Long companyId) {
        logger.debug("Entering retrieve");
        Company company = null;

        company = (Company) sessionFactory.getCurrentSession().get(Company.class,companyId);

        logger.debug("Leaving retrieve");
        return company;
    }

}
