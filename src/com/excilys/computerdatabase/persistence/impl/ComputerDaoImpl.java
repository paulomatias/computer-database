package com.excilys.computerdatabase.persistence.impl;

import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.persistence.ComputerDao;

import java.util.List;

/**
 * Project: computer-database
 * Package: com.excilys.computerdatabase.persistence.impl
 * User: lortola
 * Date: 15/11/13
 * Description: N/A
 */
public class ComputerDaoImpl implements ComputerDao {

    @Override
    public void create(Computer computer) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Computer> retrieveAll() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Computer retrieve(Long computerId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean update(Computer computer) {
        return false; //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean delete(List<Long> computerIds) {
        return false; //To change body of implemented methods use File | Settings | File Templates.
    }
}
