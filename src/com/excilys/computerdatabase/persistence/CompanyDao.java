package com.excilys.computerdatabase.persistence;

import com.excilys.computerdatabase.domain.Company;
import java.util.List;

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
