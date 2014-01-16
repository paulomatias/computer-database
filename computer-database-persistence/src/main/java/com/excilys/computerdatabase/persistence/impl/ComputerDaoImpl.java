package com.excilys.computerdatabase.persistence.impl;

import com.excilys.computerdatabase.common.Page;
import com.excilys.computerdatabase.domain.Company;
import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.persistence.ComputerDao;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.StatementCreatorUtils;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

/**
 * Project: computer-database
 * Package: com.excilys.computerdatabase.persistence.impl
 * Computer: lortola
 * Date: 15/11/13
 * Description: N/A
 */
@Repository
public class ComputerDaoImpl implements ComputerDao {

    private static Logger logger = LoggerFactory.getLogger(ComputerDaoImpl.class);

    private static final String COMPUTER_ASC = "computer.name ASC ";
    private static final String INTRODUCED_ASC = "CASE WHEN (computer.introduced IS NULL OR computer.introduced = '0000-00-00 00:00:00') THEN 1 ELSE 0 END ASC, computer.introduced ASC ";
    private static final String DISCONTINUED_ASC = "CASE WHEN (computer.discontinued IS NULL OR computer.discontinued = '0000-00-00 00:00:00') THEN 1 ELSE 0 END ASC, computer.discontinued ASC ";
    private static final String COMPANY_ASC = "CASE WHEN company.name IS NULL THEN 1 ELSE 0 END ASC, company.name ASC ";
    private static final String COMPUTER_DESC = "computer.name DESC ";
    private static final String INTRODUCED_DESC = "CASE WHEN (computer.introduced IS NULL OR computer.introduced = '0000-00-00 00:00:00') THEN 1 ELSE 0 END ASC, computer.introduced DESC ";
    private static final String DISCONTINUED_DESC = "CASE WHEN (computer.discontinued IS NULL OR computer.discontinued = '0000-00-00 00:00:00') THEN 1 ELSE 0 END ASC, computer.discontinued DESC ";
    private static final String COMPANY_DESC = "CASE WHEN company.name IS NULL THEN 1 ELSE 0 END ASC, company.name DESC ";

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Computer create(final Computer computer) throws DataAccessException {
        logger.debug("Entering create with object " + computer);

        final String sql = "INSERT INTO computer(name, introduced, discontinued, company_id) VALUES( :computerName , :introduced , :discontinued , :companyId );";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("computerName",computer.getName());
        if (computer.getIntroduced() != null)
            params.addValue("introduced",new java.sql.Date(computer.getIntroduced().getMillis()));
        else
            params.addValue("introduced", null, Types.NULL);
        if (computer.getDiscontinued() != null)
            params.addValue("discontinued",new java.sql.Date(computer.getDiscontinued().getMillis()));
        else
            params.addValue("discontinued", null, Types.NULL);
        params.addValue("companyId",computer.getCompany() == null ? null : computer.getCompany().getId(), Types.BIGINT);

        namedParameterJdbcTemplate.update(sql, params, keyHolder);

        computer.setId((Long)keyHolder.getKey());

        logger.debug("leaving create");
        return computer;
    }

    @Override
    public Page<Computer> retrieveAll() {
        logger.debug("Entering retrieveAll");
        return retrievePage(0,0,null,0);
    }

    @Override
    public Page<Computer> retrievePage(int offset, int limit, String searchString, int sort) {
        logger.debug(new StringBuilder("Entering retrievePage with offset ").append(offset)
                                        .append(" limit ").append(limit)
                                        .append(" searchString ").append(searchString)
                                        .append(" sort ").append(sort)
                                        .toString());

        List<Computer> computers = null;
        int count = 0, totalCount = 0;
        Page<Computer> computerPage = new Page<Computer>();
        computerPage.setSearchString(searchString);
        computerPage.setSort(sort);

        if(searchString != null)
            searchString = new StringBuilder().append("%").append(searchString).append("%").toString();


        final StringBuilder sql = new StringBuilder();
        sql.append("SELECT computer.id, computer.name, introduced, discontinued, company.id, company.name FROM computer LEFT JOIN company ON computer.company_id = company.id ");
        if(searchString != null)
            sql.append("WHERE computer.name LIKE :searchString OR company.name LIKE :searchString ");
        sql.append("ORDER BY ");

        final StringBuilder sql2 = new StringBuilder("SELECT count(*) AS count FROM computer LEFT JOIN company ON computer.company_id = company.id ");
        if(searchString != null)
            sql2.append("WHERE computer.name LIKE :searchString OR company.name LIKE :searchString ");

        //Sort computer
        switch(sort) {
            case 0:
                sql.append(COMPUTER_ASC).append(", ").append(INTRODUCED_ASC).append(", ").append(DISCONTINUED_ASC).append(", ").append(COMPANY_ASC);
                break;
            case 1:
                sql.append(COMPUTER_DESC).append(", ").append(INTRODUCED_ASC).append(", ").append(DISCONTINUED_ASC).append(", ").append(COMPANY_ASC);
                break;
            case 2:
                sql.append(INTRODUCED_ASC).append(", ").append(COMPUTER_ASC).append(", ").append(DISCONTINUED_ASC).append(", ").append(COMPANY_ASC);
                break;
            case 3:
                sql.append(INTRODUCED_DESC).append(", ").append(COMPUTER_ASC).append(", ").append(DISCONTINUED_ASC).append(", ").append(COMPANY_ASC);
                break;
            case 4:
                sql.append(DISCONTINUED_ASC).append(", ").append(COMPUTER_ASC).append(", ").append(INTRODUCED_ASC).append(", ").append(COMPANY_ASC);
                break;
            case 5:
                sql.append(DISCONTINUED_DESC).append(", ").append(COMPUTER_ASC).append(", ").append(INTRODUCED_ASC).append(", ").append(COMPANY_ASC);
                break;
            case 6:
                sql.append(COMPANY_ASC).append(", ").append(COMPUTER_ASC).append(", ").append(INTRODUCED_ASC).append(", ").append(DISCONTINUED_ASC);
                break;
            case 7:
                sql.append(COMPANY_DESC).append(", ").append(COMPUTER_ASC).append(", ").append(INTRODUCED_ASC).append(", ").append(DISCONTINUED_ASC);
                break;
        }

        if(limit > 0)
            sql.append("LIMIT ").append(limit).append(" ");
        if(offset > 0)
            sql.append("OFFSET ").append(offset);

        computers =namedParameterJdbcTemplate.query(sql.toString(), new MapSqlParameterSource("searchString",searchString),new ComputerRowMapper());

        computerPage.setItems(computers);

        totalCount = namedParameterJdbcTemplate.queryForObject(sql2.toString(), new MapSqlParameterSource("searchString",searchString),
            new RowMapper<Integer>() {
                @Override
                public Integer mapRow(ResultSet rs, int i) throws SQLException {
                    return rs.getInt("count");
                }
            }
        );

        logger.debug("Found " + computers.size() + " elements");

        computerPage.setItems(computers);
        computerPage.setRecordCount(count);

        if(limit > 0) {
            computerPage.setLimit(limit);
            computerPage.setCurrentPage((int) Math.floor(offset / limit) + 1);
            if(totalCount % limit == 0)
                computerPage.setPageCount((int) Math.floor(totalCount / limit));
            else
                computerPage.setPageCount((int) Math.floor(totalCount / limit) + 1);
            computerPage.setTotalCount(totalCount);
        }

        logger.debug("Leaving retrievePage");
        return computerPage;
    }

    @Override
    public Computer retrieve(Long computerId) {
        logger.debug("Entering retrieve");

        Computer computer = null;

        logger.debug("Creating statement...");

        final String sql = "SELECT computer.id, computer.name, introduced, discontinued, company.id, company.name FROM computer LEFT JOIN company on computer.company_id = company.id WHERE computer.id= :id ";

        computer = namedParameterJdbcTemplate.queryForObject(sql,new MapSqlParameterSource("id",computerId),new ComputerRowMapper());

        logger.debug("Leaving retrieve");
        return computer;
    }

    @Override
    public void update(Computer computer) {
        logger.debug("Entering update");

        final String sql = "UPDATE computer SET name= :computerName, introduced= :introduced, discontinued= :discontinued, company_id= :companyId WHERE id= :id";

        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("id",computer.getId());
        params.addValue("computerName",computer.getName());
        if (computer.getIntroduced() != null)
            params.addValue("introduced", new java.sql.Date(computer.getIntroduced().getMillis()));
        else
            params.addValue("introduced", null, Types.TIMESTAMP);
        if (computer.getDiscontinued() != null)
            params.addValue("discontinued", new java.sql.Date(computer.getDiscontinued().getMillis()));
        else
            params.addValue("discontinued", null, Types.TIMESTAMP);
        params.addValue("companyId", computer.getCompany() == null ? null : computer.getCompany().getId(), Types.BIGINT);


        namedParameterJdbcTemplate.update(sql, params);

        logger.debug("Leaving update");
    }

    @Override
    public void delete(List<Long> computerIds) {
        logger.debug("Entering delete");

        if(computerIds == null || computerIds.isEmpty()) {
            logger.debug("Nothing to delete");
            return;
        }

        final StringBuilder sql = new StringBuilder("DELETE FROM computer WHERE ");

        if(computerIds.size() == 1)
            sql.append("id = ?");
        else {

            sql.append("id IN (");
            for(int i=0;i<computerIds.size();i++) {
                sql.append("?");
                if(i < computerIds.size()-1)
                    sql.append(", ");
            }
            sql.append(")");
        }

        jdbcTemplate.update(sql.toString(),computerIds.toArray());

        logger.debug("Leaving delete");
    }

    private static class ComputerRowMapper implements RowMapper<Computer> {

        @Override
        public Computer mapRow(ResultSet rs, int i) throws SQLException {
            return Computer.builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("computer.name"))
                    .introduced(rs.getDate("introduced") == null ? null : new DateTime(rs.getDate("introduced")))
                    .discontinued(rs.getDate("discontinued") == null ? null : new DateTime(rs.getDate("discontinued")))
                    .company(new Company(rs.getLong("company.id"), rs.getString("company.name")))
                    .build();
        }
    }
}
