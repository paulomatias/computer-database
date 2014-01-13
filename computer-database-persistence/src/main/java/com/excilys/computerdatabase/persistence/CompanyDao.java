package com.excilys.computerdatabase.persistence;

import java.util.List;

import com.excilys.computerdatabase.domain.Company;

/**
 * Project: computer-database
 * Package: com.excilys.computerdatabase.persistence
 * User: lortola
 * Date: 15/11/13
 * Description: N/A
 */
public interface CompanyDao {

    public List<Company> retrieveAll();
	public Company retrieve(Long companyId);

}
