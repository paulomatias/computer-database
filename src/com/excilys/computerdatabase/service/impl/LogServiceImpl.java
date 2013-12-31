package com.excilys.computerdatabase.service.impl;

import com.excilys.computerdatabase.common.LogOperationType;
import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.domain.Log;
import com.excilys.computerdatabase.persistence.CompanyDao;
import com.excilys.computerdatabase.persistence.LogDao;
import com.excilys.computerdatabase.persistence.factory.DaoFactory;
import com.excilys.computerdatabase.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * User: lortola
 * Date: 31/12/13
 * Time: 11:49
 * To change this template use File | Settings | File Templates.
 */
public class LogServiceImpl implements LogService {

    private LogDao logDao = DaoFactory.INSTANCE.getLogDao();
    private static Logger logger = LoggerFactory.getLogger(LogServiceImpl.class);

    @Override
    public Log create(Log log) {
        return logDao.create(log);
    }
}
