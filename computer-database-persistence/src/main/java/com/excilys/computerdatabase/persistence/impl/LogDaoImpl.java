package com.excilys.computerdatabase.persistence.impl;

import com.excilys.computerdatabase.domain.Log;
import com.excilys.computerdatabase.persistence.LogDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Types;

/**
 * Created with IntelliJ IDEA.
 * User: lortola
 * Date: 31/12/13
 * Time: 10:57
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class LogDaoImpl implements LogDao {

    private static Logger logger = LoggerFactory.getLogger(ComputerDaoImpl.class);

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Log create(Log log) {
        logger.debug("Entering create with object " + log);

        final String sql = "INSERT INTO log(operation_type, operation_date, description) VALUES(:operationType,:operationDate,:description);";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("operationType",log.getOperationType().toString());
        params.addValue("description",log.getDescription());
        if (log.getOperationDate() != null)
            params.addValue("operationDate",new java.sql.Date(log.getOperationDate().getMillis()));
        else
            params.addValue("operationDate", null, Types.TIMESTAMP);

        namedParameterJdbcTemplate.update(sql, params, keyHolder);

        log.setId((Long) keyHolder.getKey());

        logger.debug("leaving create");
        return log;
    }
}
