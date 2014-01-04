package com.excilys.computerdatabase.service.impl;

import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.computerdatabase.common.LogOperationType;
import com.excilys.computerdatabase.common.Page;
import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.domain.Log;
import com.excilys.computerdatabase.persistence.ComputerDao;
import com.excilys.computerdatabase.persistence.LogDao;
import com.excilys.computerdatabase.persistence.factory.DaoFactory;
import com.excilys.computerdatabase.service.ComputerService;

/**
 * Project: computer-database
 * Package: com.excilys.computerdatabase.service.impl
 * User: lortola
 * Date: 15/11/13
 * Description: N/A
 */
@Service
public class ComputerServiceImpl implements ComputerService {

    @Autowired
    private ComputerDao computerDao;

    @Autowired
    private LogDao logDao;

    @Autowired
    private DaoFactory df;

    private static Logger logger = LoggerFactory.getLogger(ComputerServiceImpl.class);

    @Override
    public Computer create(Computer computer) {
        Computer result = null;
        df.startTransaction();
        try {

            result = computerDao.create(computer);
            Log log = Log.builder().operationDate(Calendar.getInstance().getTime())
                    .operationType(LogOperationType.COMPUTER_ADD)
                    .description("id:"+result.getId()).build();

            logDao.create(log);

            df.commitTransaction();

        } finally {
            df.closeConn();
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
        df.startTransaction();
        boolean result = false;
        try {
            result = computerDao.update(computer);
            Log log = Log.builder().operationDate(Calendar.getInstance().getTime())
                    .operationType(LogOperationType.COMPUTER_UPDATE)
                    .description("id:"+computer.getId()).build();
            logDao.create(log);
            df.commitTransaction();

        } finally {
            df.closeConn();
        }
        return result;
    }

    @Override
    public boolean delete(List<Long> computerIds) {
        df.startTransaction();
        boolean result = false;
        try {
            result = computerDao.delete(computerIds);
            Log log = Log.builder().operationDate(Calendar.getInstance().getTime())
                    .operationType(LogOperationType.COMPUTER_UPDATE)
                    .description("ids:"+computerIds.toString()).build();
            logDao.create(log);
            df.commitTransaction();

        } finally {
           df.closeConn();
        }
        return result;
    }
}
