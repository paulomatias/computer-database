package com.excilys.computerdatabase.service;

import com.excilys.computerdatabase.domain.Company;

import java.util.List;

/**
 * Project: computer-database
 * Package: com.excilys.computerdatabase.service
 * User: lortola
 * Date: 15/11/13
 * Description: N/A
 */
public interface CompanyService {

    public List<Company> retrieveAll();
    public Company retrieve(Long companyId);

}
