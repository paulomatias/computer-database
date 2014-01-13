package com.excilys.computerdatabase.service;

import com.excilys.computerdatabase.common.Page;
import com.excilys.computerdatabase.domain.Computer;

import java.util.List;

/**
 * Project: computer-database
 * Package: com.excilys.computerdatabase.service
 * User: lortola
 * Date: 15/11/13
 * Description: N/A
 */
public interface ComputerService {
    public Computer create(Computer computer);
    public Page<Computer> retrieveAll();
    public Page<Computer> retrievePage(int offset, int limit, String searchString,int sort);
    public Computer retrieve(Long computerId);
    public void update(Computer computer);
    public void delete(List<Long> computerIds);
}