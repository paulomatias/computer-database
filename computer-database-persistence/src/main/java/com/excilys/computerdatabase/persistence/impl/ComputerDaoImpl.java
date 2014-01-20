package com.excilys.computerdatabase.persistence.impl;

import com.excilys.computerdatabase.common.Page;
import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.domain.QCompany;
import com.excilys.computerdatabase.domain.QComputer;
import com.excilys.computerdatabase.persistence.ComputerDao;
import com.mysema.query.jpa.impl.JPADeleteClause;
import com.mysema.query.jpa.impl.JPAQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
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
        JPAQuery query = new JPAQuery(em);
        query.from(QComputer.computer)
             .leftJoin(QComputer.computer.company,QCompany.company);

        //Result Count query
        JPAQuery query2 = new JPAQuery(em);
        query2.from(QComputer.computer)
              .leftJoin(QComputer.computer.company, QCompany.company);

        //Search restriction
        if(searchString != null && !searchString.trim().isEmpty()) {
            query.where(QComputer.computer.name.toLowerCase().contains(searchString).or(QCompany.company.name.toLowerCase().contains(searchString)));

            query2.where(QComputer.computer.name.toLowerCase().contains(searchString).or(QCompany.company.name.toLowerCase().contains(searchString)));

        }

        //Sort computer
        switch(sort) {
            case 0:
                query.orderBy(QComputer.computer.name.asc());
                break;
            case 1:
                query.orderBy(QComputer.computer.name.desc());
                break;
            case 2:
                query.orderBy(QComputer.computer.introduced.asc());
                break;
            case 3:
                query.orderBy(QComputer.computer.introduced.desc());
                break;
            case 4:
                query.orderBy(QComputer.computer.discontinued.asc());
                break;
            case 5:
                query.orderBy(QComputer.computer.discontinued.desc());
                break;
            case 6:
                query.orderBy(QComputer.computer.company.name.asc());
                break;
            case 7:
                query.orderBy(QComputer.computer.company.name.desc());
                break;
        }

        //Pagination
        if(limit>0) {
            query.offset(offset).limit(limit);
        }

        computers = query.fetch().list(QComputer.computer);

        computerPage.setItems(computers);

        totalCount = ((Long)query2.count()).intValue();

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

        JPAQuery query = new JPAQuery(em);
        query.from(QComputer.computer)
             .where(QComputer.computer.id.eq(computerId));

        computer = query.uniqueResult(QComputer.computer);

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


        JPADeleteClause query = new JPADeleteClause(em,QComputer.computer);
        query.where(QComputer.computer.id.in(computerIds)).execute();

        logger.debug("Leaving delete");
    }

}
