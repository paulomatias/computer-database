package com.excilys.computerdatabase.persistence.impl;

import com.excilys.computerdatabase.domain.Company;
import com.excilys.computerdatabase.domain.QCompany;
import com.excilys.computerdatabase.persistence.CompanyDao;
import com.mysema.query.jpa.impl.JPAQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.List;

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

    @PersistenceContext(unitName = "computerDatabasePU", type = PersistenceContextType.TRANSACTION)
    private EntityManager em;
 
    @SuppressWarnings("unchecked")
	@Override
    public List<Company> retrieveAll() {
        logger.debug("Entering retrieveAll");
        List<Company> companies = null;

        JPAQuery query = new JPAQuery(em);
        query.from(QCompany.company);

        companies = query.list(QCompany.company);

        logger.debug(new StringBuilder("Found ").append(companies.size()).append(" elements").toString());

        logger.debug("Leaving retrieveAll");

        return companies;
    }

    @Override
    public Company retrieve(Long companyId) {
        logger.debug("Entering retrieve");
        Company company = null;

        JPAQuery query = new JPAQuery(em);
        query.from(QCompany.company)
             .where(QCompany.company.id.eq(companyId));

        company = query.uniqueResult(QCompany.company);

        logger.debug("Leaving retrieve");

        return company;
    }

}
