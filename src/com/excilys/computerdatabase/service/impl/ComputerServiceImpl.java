package com.excilys.computerdatabase.service.impl;

import com.excilys.computerdatabase.common.Page;
import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.persistence.ComputerDao;
import com.excilys.computerdatabase.persistence.factory.DaoFactory;
import com.excilys.computerdatabase.service.ComputerService;

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

    @Override
    public void create(Computer computer) {
        computerDao.create(computer);
    }

    @Override
    public Page<Computer> retrieveAll() {
        return computerDao.retrieveAll();
    }

    @Override
    public Computer retrieve(Long computerId) {
        return computerDao.retrieve(computerId);
    }

    @Override
    public boolean update(Computer computer) {
        return computerDao.update(computer);
    }

    @Override
    public boolean delete(List<Long> computerIds) {
        return computerDao.delete(computerIds);
    }
}
