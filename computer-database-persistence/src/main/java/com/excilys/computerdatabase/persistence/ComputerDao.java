package com.excilys.computerdatabase.persistence;


import java.sql.SQLException;
import java.util.List;

import com.excilys.computerdatabase.common.Page;
import com.excilys.computerdatabase.domain.Computer;
import org.springframework.dao.DataAccessException;

/**
 * Project: computer-database
 * Package: com.excilys.computerdatabase.persistence
 * User: lortola
 * Date: 15/11/13
 * Description: N/A
 */
public interface ComputerDao {

    public Computer create(Computer computer) throws DataAccessException;
    public Page<Computer> retrieveAll();
    public Page<Computer> retrievePage(int offset, int limit, String searchString, int sort);
    public Computer retrieve(Long computerId);
    public void update(Computer computer);
    public void delete(List<Long> computerIds);
}
