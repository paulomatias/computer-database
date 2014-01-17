package com.excilys.computerdatabase.persistence.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
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

    private static final String COMPUTER_ASC = "computer.name ASC ";
    private static final String INTRODUCED_ASC = "CASE WHEN (computer.introduced IS NULL OR computer.introduced = '0000-00-00 00:00:00') THEN 1 ELSE 0 END ASC, computer.introduced ASC ";
    private static final String DISCONTINUED_ASC = "CASE WHEN (computer.discontinued IS NULL OR computer.discontinued = '0000-00-00 00:00:00') THEN 1 ELSE 0 END ASC, computer.discontinued ASC ";
    private static final String COMPANY_ASC = "CASE WHEN company.name IS NULL THEN 1 ELSE 0 END ASC, company.name ASC ";
    private static final String COMPUTER_DESC = "computer.name DESC ";
    private static final String INTRODUCED_DESC = "CASE WHEN (computer.introduced IS NULL OR computer.introduced = '0000-00-00 00:00:00') THEN 1 ELSE 0 END ASC, computer.introduced DESC ";
    private static final String DISCONTINUED_DESC = "CASE WHEN (computer.discontinued IS NULL OR computer.discontinued = '0000-00-00 00:00:00') THEN 1 ELSE 0 END ASC, computer.discontinued DESC ";
    private static final String COMPANY_DESC = "CASE WHEN company.name IS NULL THEN 1 ELSE 0 END ASC, company.name DESC ";

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
        int count = 0, totalCount = 0;
        Page<Computer> computerPage = new Page<Computer>();
        computerPage.setSearchString(searchString);
        computerPage.setSort(sort);

        if(searchString != null)
            searchString = new StringBuilder().append("%").append(searchString).append("%").toString();


        final StringBuilder sql = new StringBuilder();
        sql.append("SELECT computer FROM Computer computer LEFT JOIN computer.company company ");

        if(searchString != null)
            sql.append("WHERE computer.name LIKE :searchString OR company.name LIKE :searchString ");
        sql.append("ORDER BY ");

        final StringBuilder sql2 = new StringBuilder("SELECT count(*) FROM Computer computer LEFT JOIN computer.company company ");
        if(searchString != null)
            sql2.append("WHERE computer.name LIKE :searchString OR company.name LIKE :searchString ");

        //Sort computer
        switch(sort) {
            case 0:
                sql.append(COMPUTER_ASC).append(", ").append(INTRODUCED_ASC).append(", ").append(DISCONTINUED_ASC).append(", ").append(COMPANY_ASC);
                break;
            case 1:
                sql.append(COMPUTER_DESC).append(", ").append(INTRODUCED_ASC).append(", ").append(DISCONTINUED_ASC).append(", ").append(COMPANY_ASC);
                break;
            case 2:
                sql.append(INTRODUCED_ASC).append(", ").append(COMPUTER_ASC).append(", ").append(DISCONTINUED_ASC).append(", ").append(COMPANY_ASC);
                break;
            case 3:
                sql.append(INTRODUCED_DESC).append(", ").append(COMPUTER_ASC).append(", ").append(DISCONTINUED_ASC).append(", ").append(COMPANY_ASC);
                break;
            case 4:
                sql.append(DISCONTINUED_ASC).append(", ").append(COMPUTER_ASC).append(", ").append(INTRODUCED_ASC).append(", ").append(COMPANY_ASC);
                break;
            case 5:
                sql.append(DISCONTINUED_DESC).append(", ").append(COMPUTER_ASC).append(", ").append(INTRODUCED_ASC).append(", ").append(COMPANY_ASC);
                break;
            case 6:
                sql.append(COMPANY_ASC).append(", ").append(COMPUTER_ASC).append(", ").append(INTRODUCED_ASC).append(", ").append(DISCONTINUED_ASC);
                break;
            case 7:
                sql.append(COMPANY_DESC).append(", ").append(COMPUTER_ASC).append(", ").append(INTRODUCED_ASC).append(", ").append(DISCONTINUED_ASC);
                break;
        }

        Query query = sessionFactory.getCurrentSession().createQuery(sql.toString()).setParameter("searchString",searchString).setMaxResults(limit).setFirstResult(offset);

        computers = query.list();

        computerPage.setItems(computers);

        totalCount = ((Long)(sessionFactory.getCurrentSession().createQuery(sql2.toString()).setParameter("searchString",searchString).iterate().next())).intValue();

        logger.debug("Found " + computers.size() + " elements");

        computerPage.setItems(computers);
        computerPage.setRecordCount(count);

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
