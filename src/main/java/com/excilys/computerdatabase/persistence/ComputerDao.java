package com.excilys.computerdatabase.persistence;


import java.util.List;

import com.excilys.computerdatabase.common.Page;
import com.excilys.computerdatabase.domain.Computer;

/**
 * Project: computer-database
 * Package: com.excilys.computerdatabase.persistence
 * User: lortola
 * Date: 15/11/13
 * Description: N/A
 */
public interface ComputerDao {

    public Computer create(Computer computer);
    public Page<Computer> retrieveAll();
    public Page<Computer> retrievePage(int offset, int limit, String searchString, int sort);
    public Computer retrieve(Long computerId);
    public boolean update(Computer computer);
    public boolean delete(List<Long> computerIds);
}
