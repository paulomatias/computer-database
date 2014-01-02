package com.excilys.computerdatabase.validator;

import com.excilys.computerdatabase.domain.Company;
import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.dto.ComputerDto;
import com.excilys.computerdatabase.persistence.factory.DaoFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Project: computer-database
 * Package: com.excilys.computerdatabase.validator
 * User: lortola
 * Date: 25/11/13
 * Description: N/A
 */
public class ComputerValidator implements Validator<ComputerDto> {

    private static Logger logger = LoggerFactory.getLogger(ComputerValidator.class);
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    public int validate(ComputerDto obj) {
        int errorBits = 0;
        //Computer validation process
        if(obj.getName() != null && !obj.getName().trim().isEmpty()) {
            logger.debug("Param name detected. Value is: " + obj.getName());
        }
        else {
            logger.info("No name was provided");
            errorBits += 1;
        }

        if(obj.getIntroduced() != null && !obj.getIntroduced().trim().isEmpty()) {
            try {
                logger.debug("Param introduced detected. Value is: " + obj.getIntroduced());
                Date introduced = DATE_FORMAT.parse(obj.getIntroduced());
            } catch (ParseException e) {
                logger.info("Invalid introduced date was set");
                errorBits +=2;
            }
        }

        if(obj.getDiscontinued() != null && !obj.getDiscontinued().trim().isEmpty()) {
            try {
                logger.debug("Param discontinued detected. Value is: " + obj.getDiscontinued());
                Date discontinued = DATE_FORMAT.parse(obj.getDiscontinued());
            } catch (ParseException e) {
                logger.info("Invalid discontinued date was set");
                errorBits +=4;
            }
        }

        if(obj.getCompanyId() != null && !obj.getCompanyId().equals(0)) {
            logger.debug("Param companyId detected. Value is: " + obj.getCompanyId());
            Long companyId = obj.getCompanyId();
            if(companyId <= 0)
                logger.info("Invalid company was set");
        }

        return errorBits;

    }
}
