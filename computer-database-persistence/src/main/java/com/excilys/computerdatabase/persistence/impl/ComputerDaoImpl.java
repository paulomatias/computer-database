package com.excilys.computerdatabase.persistence.impl;

import com.excilys.computerdatabase.common.Page;
import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.persistence.ComputerDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.List;

/**
 * Project: computer-database
 * Package: com.excilys.computerdatabase.persistence.impl
 * Computer: lortola
 * Date: 15/11/13
 * Description: N/A
 */
@Repository
public class ComputerDaoImpl implements ComputerDao {

    private static Logger logger = LoggerFactory.getLogger(ComputerDaoImpl.class);

    @PersistenceContext(unitName = "computerDatabasePU", type = PersistenceContextType.TRANSACTION)
    private EntityManager em;

    @Override
    public Computer create(final Computer computer) throws DataAccessException {
        logger.debug("Entering create with object " + computer);

        em.persist(computer);

        logger.debug("leaving create");
        return computer;
    }

    @Override
    public Page<Computer> retrieveAll() {
        logger.debug("Entering retrieveAll");
        return retrievePage(0,0,null,0);
    }

    @SuppressWarnings("unchecked")
	@Override
    public Page<Computer> retrievePage(int offset, int limit, String searchString, int sort) {
        logger.debug(new StringBuilder("Entering retrievePage with offset ").append(offset)
                                        .append(" limit ").append(limit)
                                        .append(" searchString ").append(searchString)
                                        .append(" sort ").append(sort)
                                        .toString());

        List<Computer> computers = null;
        int totalCount = 0;
        Page<Computer> computerPage = new Page<Computer>();
        computerPage.setSearchString(searchString);
        computerPage.setSort(sort);

        //Results query
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Computer> criteriaQuery = cb.createQuery(Computer.class);
        Root<Computer> root = criteriaQuery.from(Computer.class);
        root.join("company", JoinType.LEFT);

        //Result Count query
        CriteriaQuery<Long> criteriaQuery2 = cb.createQuery(Long.class);
        Root<Computer> root2 = criteriaQuery2.from(Computer.class);
        root2.join("company", JoinType.LEFT);

        //Search restriction
        if(searchString != null && !searchString.isEmpty()) {
            Predicate clause = cb.or(cb.like(root.<String>get("name"), searchString),cb.like(root.<String>get("computer.name"), searchString));
            criteriaQuery.where(clause);
            criteriaQuery2.where(clause);
        }

        //Sort computer
        switch(sort) {
            case 0:
                criteriaQuery.orderBy(cb.asc(root.get("name")));
                break;
            case 1:
                criteriaQuery.orderBy(cb.desc(root.get("name")));
                break;
            case 2:
                criteriaQuery.orderBy(cb.asc(root.get("introduced")));
                break;
            case 3:
                criteriaQuery.orderBy(cb.desc(root.get("introduced")));
                break;
            case 4:
                criteriaQuery.orderBy(cb.asc(root.get("discontinued")));
                break;
            case 5:
                criteriaQuery.orderBy(cb.desc(root.get("discontinued")));
                break;
            case 6:
                criteriaQuery.orderBy(cb.asc(root.get("company.name")));
                break;
            case 7:
                criteriaQuery.orderBy(cb.desc(root.get("company.name")));
                break;
        }

        //Pagination
        Query query = em.createQuery(criteriaQuery.select(root));

        query.setFirstResult(offset);
        if(limit>0) {
            query.setMaxResults(limit);
        }

        computers = query.getResultList();

        computerPage.setItems(computers);

        //Count (executing second query)
        totalCount = em.createQuery(criteriaQuery2.select(cb.count(root2))).getSingleResult().intValue();

        logger.debug("Found " + computers.size() + " elements");

        computerPage.setItems(computers);
        computerPage.setRecordCount(computers.size());

        if(limit > 0) {
            computerPage.setLimit(limit);
            computerPage.setCurrentPage((int) Math.floor(offset / limit) + 1);
            if(totalCount % limit == 0)
                computerPage.setPageCount((int) Math.floor(totalCount / limit));
            else
                computerPage.setPageCount((int) Math.floor(totalCount / limit) + 1);
            computerPage.setTotalCount(totalCount);
        }

        logger.debug("Leaving retrievePage");
        return computerPage;
    }

    @Override
    public Computer retrieve(Long computerId) {
        logger.debug("Entering retrieve");

        Computer computer = null;

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Computer> criteriaQuery = cb.createQuery(Computer.class);
        Root<Computer> root = criteriaQuery.from(Computer.class);
        criteriaQuery.where(cb.equal(root.<Long>get("id"),computerId));

        computer = em.createQuery(criteriaQuery.select(root)).getSingleResult();

        logger.debug("Leaving retrieve");
        return computer;
    }

    @Override
    public void update(Computer computer) {
        logger.debug("Entering update");

        em.merge(computer);

        logger.debug("Leaving update");
    }

    @Override
    public void delete(List<Long> computerIds) {
        logger.debug("Entering delete");



        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaDelete<Computer> criteriaDelete = cb.createCriteriaDelete(Computer.class);
        Root<Computer> root = criteriaDelete.from(Computer.class);
        criteriaDelete.where(root.<Long>get("id").in(computerIds));

        em.createQuery(criteriaDelete).executeUpdate();

        logger.debug("Leaving delete");
    }

}
