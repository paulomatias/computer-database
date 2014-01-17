package com.excilys.computerdatabase.persistence.impl;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.domain.Log;
import com.excilys.computerdatabase.persistence.LogDao;

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

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Log create(Log log) {
        logger.debug("Entering create with object " + log);

        sessionFactory.getCurrentSession().persist(log);

        logger.debug("leaving create");
        return log;
    }
}
