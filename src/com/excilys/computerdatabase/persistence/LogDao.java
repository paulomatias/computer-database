package com.excilys.computerdatabase.persistence;

import com.excilys.computerdatabase.domain.Company;
import com.excilys.computerdatabase.domain.Log;

import java.sql.Connection;
import java.util.List;

/**
 * Project: computer-database
 * Package: com.excilys.computerdatabase.persistence
 * User: lortola
 * Date: 15/11/13
 * Description: N/A
 */
public interface LogDao {

    public Log create(Log log);

}
