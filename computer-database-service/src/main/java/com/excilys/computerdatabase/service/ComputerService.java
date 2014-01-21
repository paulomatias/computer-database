package com.excilys.computerdatabase.service;

import com.excilys.computerdatabase.domain.Computer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
    public Page<Computer> retrievePage(Pageable page, String searchString);
    public Computer retrieve(Long computerId);
    public void update(Computer computer);
    public void delete(List<Long> computerIds);
}