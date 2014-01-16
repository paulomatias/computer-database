package com.excilys.computerdatabase.persistence.impl;

import com.excilys.computerdatabase.domain.Company;
import com.excilys.computerdatabase.persistence.CompanyDao;
import com.jolbox.bonecp.BoneCPDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Project: computer-database
 * Package: com.excilys.computerdatabase.persistence.impl
 * User: lortola
 * Date: 15/11/13
 * Description: N/A
 */
@Repository
public class CompanyDaoImpl implements CompanyDao {

    private static Logger logger = LoggerFactory.getLogger(CompanyDaoImpl.class);

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
 
    @Override
    public List<Company> retrieveAll() {
        logger.debug("Entering retrieveAll");
        List<Company> companies = null;

        String sql = "SELECT id, name FROM company;";

        companies = namedParameterJdbcTemplate.query(sql,new CompanyRowMapper());

        logger.debug(new StringBuilder("Found ").append(companies.size()).append(" elements").toString());
        logger.debug("Leaving retrieveAll");

        return companies;
    }

    @Override
    public Company retrieve(Long companyId) {
        logger.debug("Entering retrieve");
        Company company = null;

        String sql = "SELECT id, name FROM company WHERE id= :id ";

        company = namedParameterJdbcTemplate.queryForObject(sql,new MapSqlParameterSource("id",companyId),new CompanyRowMapper());

        logger.debug("Leaving retrieve");
        return company;
    }

    private static class CompanyRowMapper implements RowMapper<Company> {

        @Override
        public Company mapRow(ResultSet rs, int i) throws SQLException {
            return new Company(rs.getLong("id"),rs.getString("name"));
        }
    }

}
