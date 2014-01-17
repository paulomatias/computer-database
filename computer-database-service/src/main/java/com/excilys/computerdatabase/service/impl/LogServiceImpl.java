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
 * Project: computer-database
 * Package: com.excilys.computerdatabase.service.impl
 * User: lortola
 * Date: 31/12/13
 * Description: N/A
 */
@Service
@Transactional(readOnly = true)
public class LogServiceImpl implements LogService {

    @Autowired
    private LogDao logDao;

    @SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(LogServiceImpl.class);

    @Override
    @Transactional(readOnly = false)
    public Log create(Log log) {
        return logDao.create(log);
    }
}
