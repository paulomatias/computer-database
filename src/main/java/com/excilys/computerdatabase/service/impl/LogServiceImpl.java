package com.excilys.computerdatabase.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.computerdatabase.domain.Log;
import com.excilys.computerdatabase.persistence.LogDao;
import com.excilys.computerdatabase.service.LogService;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA.
 * User: lortola
 * Date: 31/12/13
 * Time: 11:49
 * To change this template use File | Settings | File Templates.
 */
@Service
@Transactional(readOnly = true)
public class LogServiceImpl implements LogService {

    @Autowired
    private LogDao logDao;

    private static Logger logger = LoggerFactory.getLogger(LogServiceImpl.class);

    @Override
    @Transactional(readOnly = false)
    public Log create(Log log) {
        return logDao.create(log);
    }
}
