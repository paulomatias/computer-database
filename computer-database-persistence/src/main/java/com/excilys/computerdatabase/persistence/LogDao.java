package com.excilys.computerdatabase.persistence;

import com.excilys.computerdatabase.domain.Log;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Project: computer-database
 * Package: com.excilys.computerdatabase.persistence
 * User: lortola
 * Date: 31/12/13
 * Description: N/A
 */
public interface LogDao extends JpaRepository<Log,Long> {

}
