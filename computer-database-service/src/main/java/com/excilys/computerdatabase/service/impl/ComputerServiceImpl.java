package com.excilys.computerdatabase.service.impl;

import com.excilys.computerdatabase.common.LogOperationType;
import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.domain.Log;
import com.excilys.computerdatabase.persistence.ComputerDao;
import com.excilys.computerdatabase.persistence.LogDao;
import com.excilys.computerdatabase.service.ComputerService;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(ComputerServiceImpl.class);

    @Override
    @Transactional(readOnly = false)
    public Computer create(Computer computer) {
        Computer result = null;
        result = computerDao.save(computer);
        Log log = Log.builder().operationDate(DateTime.now())
                .operationType(LogOperationType.COMPUTER_ADD)
                .description("id:" + result.getId()).build();

        logDao.save(log);
        return result;
    }

    @Override
    public Page<Computer> retrievePage(Pageable page, String searchString) {

        return computerDao.findByNameContainingOrCompanyNameContaining(searchString, searchString, page);
    }

    @Override
    public Computer retrieve(Long computerId) {
        return computerDao.findOne(computerId);
    }

    @Override
    @Transactional(readOnly = false)
    public void update(Computer computer) {
        
    	computerDao.save(computer);
        Log log = Log.builder().operationDate(DateTime.now())
                .operationType(LogOperationType.COMPUTER_UPDATE)
                .description("id:"+computer.getId()).build();
        logDao.save(log);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(List<Long> computerIds) {
        
    	computerDao.deleteList(computerIds);
        Log log = Log.builder().operationDate(DateTime.now())
                .operationType(LogOperationType.COMPUTER_UPDATE)
                .description("ids:"+computerIds.toString()).build();
        logDao.save(log);
    }
}
