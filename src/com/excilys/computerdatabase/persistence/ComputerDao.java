package com.excilys.computerdatabase.persistence;


import com.excilys.computerdatabase.domain.Computer;
import java.util.List;

/**
 * Project: computer-database
 * Package: com.excilys.computerdatabase.persistence
 * User: lortola
 * Date: 15/11/13
 * Description: N/A
 */
public interface ComputerDao {

    public void create(Computer computer);
    public List<Computer> retrieveAll();
    public Computer retrieve(Long computerId);
    public boolean update(Computer computer);
    public boolean delete(List<Long> computerIds);
}
