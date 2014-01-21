package com.excilys.computerdatabase.persistence;


import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.persistence.custom.ComputerDaoCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

/**
 * Project: computer-database
 * Package: com.excilys.computerdatabase.persistence
 * User: lortola
 * Date: 15/11/13
 * Description: N/A
 */
public interface ComputerDao extends CrudRepository<Computer,Long>, ComputerDaoCustom {
    Page<Computer> findByNameContainingOrCompanyNameContaining(String name, String companyName, Pageable pageable);
}
