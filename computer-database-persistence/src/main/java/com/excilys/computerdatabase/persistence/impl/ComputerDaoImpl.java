package com.excilys.computerdatabase.persistence.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.common.Page;
import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.persistence.ComputerDao;

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

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Computer create(final Computer computer) throws DataAccessException {
        logger.debug("Entering create with object " + computer);

        sessionFactory.getCurrentSession().persist(computer);

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
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Computer.class);
        criteria.setFetchMode("company", FetchMode.JOIN).createAlias("company","company", JoinType.LEFT_OUTER_JOIN);

        //Result Count query
        Criteria criteriaCount = sessionFactory.getCurrentSession().createCriteria(Computer.class);
        criteriaCount.setFetchMode("company", FetchMode.JOIN).createAlias("company", "company", JoinType.LEFT_OUTER_JOIN);

        //Search restriction
        if(searchString != null && !searchString.isEmpty()) {
            criteria.add(Restrictions.disjunction().add(Restrictions.like("name", searchString, MatchMode.ANYWHERE))
                    .add(Restrictions.like("company.name", searchString, MatchMode.ANYWHERE)));
            criteriaCount.add(Restrictions.disjunction().add(Restrictions.like("name", searchString, MatchMode.ANYWHERE))
                    .add(Restrictions.like("company.name", searchString, MatchMode.ANYWHERE)));
        }

        //Sort computer
        switch(sort) {
            case 0:
                criteria.addOrder(Order.asc("name"));
                break;
            case 1:
                criteria.addOrder(Order.desc("name"));
                break;
            case 2:
                criteria.addOrder(Order.asc("introduced"));
                break;
            case 3:
                criteria.addOrder(Order.desc("introduced"));
                break;
            case 4:
                criteria.addOrder(Order.asc("discontinued"));
                break;
            case 5:
                criteria.addOrder(Order.desc("discontinued"));
                break;
            case 6:
                criteria.addOrder(Order.asc("company.name"));
                break;
            case 7:
                criteria.addOrder(Order.desc("company.name"));
                break;
        }

        //Pagination
        criteria.setFirstResult(offset);
        if(limit>0) {
            criteria.setMaxResults(limit).setFetchSize(limit);
        }

        computers = criteria.list();

        computerPage.setItems(computers);

        totalCount = ((Long)criteriaCount.setProjection(Projections.rowCount()).uniqueResult()).intValue();

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

        computer = (Computer)sessionFactory.getCurrentSession().get(Computer.class,computerId);

        logger.debug("Leaving retrieve");
        return computer;
    }

    @Override
    public void update(Computer computer) {
        logger.debug("Entering update");

        sessionFactory.getCurrentSession().merge(computer);

        logger.debug("Leaving update");
    }

    @Override
    public void delete(List<Long> computerIds) {
        logger.debug("Entering delete");

        sessionFactory.getCurrentSession().createQuery("DELETE FROM Computer computer WHERE computer.id IN ( :computerIds )").setParameterList("computerIds",computerIds).executeUpdate();

        logger.debug("Leaving delete");
    }

}
