package com.excilys.computerdatabase.persistence.impl;

import com.excilys.computerdatabase.domain.Company;
import com.excilys.computerdatabase.persistence.CompanyDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Company> criteriaQuery = cb.createQuery(Company.class);
        Root<Company> root = criteriaQuery.from(Company.class);

        companies = em.createQuery(criteriaQuery.select(root)).getResultList();

        logger.debug(new StringBuilder("Found ").append(companies.size()).append(" elements").toString());

        logger.debug("Leaving retrieveAll");

        return companies;
    }

    @Override
    public Company retrieve(Long companyId) {
        logger.debug("Entering retrieve");
        Company company = null;

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Company> criteriaQuery = cb.createQuery(Company.class);
        Root<Company> root = criteriaQuery.from(Company.class);

        company = em.createQuery(criteriaQuery.select(root).where(cb.equal(root.<Long>get("id"),companyId))).getSingleResult();

        logger.debug("Leaving retrieve");

        return company;
    }

}
