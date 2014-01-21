package com.excilys.computerdatabase.persistence.impl;

import com.excilys.computerdatabase.domain.Log;
import com.excilys.computerdatabase.persistence.LogDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

/**
 * Project: computer-database
 * Package: com.excilys.computerdatabase.persistence.impl
 * User: lortola
 * Date: 31/12/13
 * Description: N/A
 */
@Repository
public class LogDaoImpl implements LogDao {

    private static Logger logger = LoggerFactory.getLogger(ComputerDaoImpl.class);

    @PersistenceContext(unitName = "computerDatabasePU", type = PersistenceContextType.TRANSACTION)
    private EntityManager em;

    @Override
    public Log create(Log log) {
        logger.debug("Entering create with object " + log);

        em.persist(log);

        logger.debug("leaving create");
        return log;
    }
}
