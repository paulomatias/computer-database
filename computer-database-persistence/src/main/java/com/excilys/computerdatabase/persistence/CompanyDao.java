package com.excilys.computerdatabase.persistence;

import com.excilys.computerdatabase.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Project: computer-database
 * Package: com.excilys.computerdatabase.persistence
 * User: lortola
 * Date: 15/11/13
 * Description: N/A
 */
public interface CompanyDao extends JpaRepository<Company,Long> {

}
