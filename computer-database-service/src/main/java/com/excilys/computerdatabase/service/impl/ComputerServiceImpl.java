package com.excilys.computerdatabase.service.impl;

import com.excilys.computerdatabase.common.LogOperationType;
import com.excilys.computerdatabase.common.Page;
import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.domain.Log;
import com.excilys.computerdatabase.persistence.ComputerDao;
import com.excilys.computerdatabase.persistence.LogDao;
import com.excilys.computerdatabase.service.ComputerService;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Project: computer-database
 * Package: com.excilys.computerdatabase.service.impl
 * User: lortola
 * Date: 15/11/13
 * Description: N/A
 */
@Service
@Transactional(readOnly = true)
public class ComputerServiceImpl implements ComputerService {

    @Autowired
    private ComputerDao computerDao;

    @Autowired
    private LogDao logDao;

    private static Logger logger = LoggerFactory.getLogger(ComputerServiceImpl.class);

    @Override
    @Transactional(readOnly = false)
    public Computer create(Computer computer) {
        Computer result = null;
        result = computerDao.create(computer);
        Log log = Log.builder().operationDate(DateTime.now())
                .operationType(LogOperationType.COMPUTER_ADD)
                .description("id:" + result.getId()).build();

        logDao.create(log);
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
    @Transactional(readOnly = false)
    public void update(Computer computer) {
        boolean result = false;
        computerDao.update(computer);
        Log log = Log.builder().operationDate(DateTime.now())
                .operationType(LogOperationType.COMPUTER_UPDATE)
                .description("id:"+computer.getId()).build();
        logDao.create(log);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(List<Long> computerIds) {
        boolean result = false;
        computerDao.delete(computerIds);
        Log log = Log.builder().operationDate(DateTime.now())
                .operationType(LogOperationType.COMPUTER_UPDATE)
                .description("ids:"+computerIds.toString()).build();
        logDao.create(log);
    }
}
