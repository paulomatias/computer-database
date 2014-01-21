package com.excilys.computerdatabase.persistence.impl;

import com.excilys.computerdatabase.domain.QComputer;
import com.excilys.computerdatabase.persistence.custom.ComputerDaoCustom;
import com.mysema.query.jpa.impl.JPADeleteClause;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.List;

/**
 * Project: computer-database
 * Package: com.excilys.computerdatabase.persistence.impl
 * Computer: lortola
 * Date: 15/11/13
 * Description: N/A
 */
public class ComputerDaoImpl implements ComputerDaoCustom {

    private static Logger logger = LoggerFactory.getLogger(ComputerDaoImpl.class);

    @PersistenceContext(unitName = "computerDatabasePU", type = PersistenceContextType.TRANSACTION)
    private EntityManager em;

    @Override
    public void deleteList(List<Long> computerIds) {
        logger.debug("Entering delete");

        new JPADeleteClause(em,QComputer.computer)
            .where(QComputer.computer.id.in(computerIds))
            .execute();

        logger.debug("Leaving delete");
    }

}
