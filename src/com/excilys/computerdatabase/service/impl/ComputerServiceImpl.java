package com.excilys.computerdatabase.service.impl;

import com.excilys.computerdatabase.common.LogOperationType;
import com.excilys.computerdatabase.common.Page;
import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.domain.Log;
import com.excilys.computerdatabase.persistence.ComputerDao;
import com.excilys.computerdatabase.persistence.LogDao;
import com.excilys.computerdatabase.persistence.factory.DaoFactory;
import com.excilys.computerdatabase.service.ComputerService;
import com.sun.org.apache.xpath.internal.functions.FuncFalse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

/**
 * Project: computer-database
 * Package: com.excilys.computerdatabase.service.impl
 * User: lortola
 * Date: 15/11/13
 * Description: N/A
 */
public class ComputerServiceImpl implements ComputerService {

    private ComputerDao computerDao = DaoFactory.INSTANCE.getComputerDao();
    private LogDao logDao = DaoFactory.INSTANCE.getLogDao();

    private static Logger logger = LoggerFactory.getLogger(ComputerServiceImpl.class);

    @Override
    public Computer create(Computer computer) {
        Computer result = null;
        DaoFactory.INSTANCE.startTransaction();
        try {

            result = computerDao.create(computer);
            Log log = Log.builder().operationDate(Calendar.getInstance().getTime())
                                   .operationType(LogOperationType.COMPUTER_ADD)
                                   .description("id:"+result.getId()).build();

            logDao.create(log);

            DaoFactory.INSTANCE.commitTransaction();

        } finally {
            DaoFactory.INSTANCE.closeConn();
        }

        return result;
    }

    @Override
    public Page<Computer> retrieveAll() {
        return computerDao.retrieveAll();
    }

    @Override
    public Page<Computer> retrievePage(int offset, int limit, String searchString,int sort) {
        return computerDao.retrievePage(offset, limit, searchString, sort);
    }

    @Override
    public Computer retrieve(Long computerId) {
        return computerDao.retrieve(computerId);
    }

    @Override
    public boolean update(Computer computer) {
        DaoFactory.INSTANCE.startTransaction();
        boolean result = false;
        try {
            result = computerDao.update(computer);
            Log log = Log.builder().operationDate(Calendar.getInstance().getTime())
                    .operationType(LogOperationType.COMPUTER_UPDATE)
                    .description("id:"+computer.getId()).build();
            logDao.create(log);
            DaoFactory.INSTANCE.commitTransaction();

        } finally {
            DaoFactory.INSTANCE.closeConn();
        }
        return result;
    }

    @Override
    public boolean delete(List<Long> computerIds) {
        DaoFactory.INSTANCE.startTransaction();
        boolean result = false;
        try {
            result = computerDao.delete(computerIds);
            Log log = Log.builder().operationDate(Calendar.getInstance().getTime())
                    .operationType(LogOperationType.COMPUTER_UPDATE)
                    .description("ids:"+computerIds.toString()).build();
            logDao.create(log);
            DaoFactory.INSTANCE.commitTransaction();

        } finally {
           DaoFactory.INSTANCE.closeConn();
        }
        return result;
    }
}
