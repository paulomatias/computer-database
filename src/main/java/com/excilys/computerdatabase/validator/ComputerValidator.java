package com.excilys.computerdatabase.validator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.dto.ComputerDto;

/**
 * Project: computer-database
 * Package: com.excilys.computerdatabase.validator
 * User: lortola
 * Date: 25/11/13
 * Description: N/A
 */
@Component
public class ComputerValidator implements Validator {

    private static Logger logger = LoggerFactory.getLogger(ComputerValidator.class);
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    public boolean supports(Class<?> aClass) {
        if(ComputerDto.class.equals(aClass))
            return true;
        return false;
    }

    @Override
    @SuppressWarnings("unused")
    public void validate(Object o, Errors errors) {
        ComputerDto obj = (ComputerDto) o;

        if(obj.getName() != null && !obj.getName().trim().isEmpty()) {
            logger.debug("Param name detected. Value is: " + obj.getName());
        }
        else {
            logger.info("No name was provided");
            errors.rejectValue("name","computer.name.error","Invalid name provided");
        }

        if(obj.getIntroduced() != null && !obj.getIntroduced().trim().isEmpty()) {
            try {
                logger.debug("Param introduced detected. Value is: " + obj.getIntroduced());
                Date introduced = DATE_FORMAT.parse(obj.getIntroduced());
            } catch (ParseException e) {
                logger.info("Invalid introduced date was set");
                errors.rejectValue("introduced","computer.introduced.error","Invalid introduced date provided");
            }
        }

        if(obj.getDiscontinued() != null && !obj.getDiscontinued().trim().isEmpty()) {
            try {
                logger.debug("Param discontinued detected. Value is: " + obj.getDiscontinued());
                Date discontinued = DATE_FORMAT.parse(obj.getDiscontinued());
            } catch (ParseException e) {
                logger.info("Invalid discontinued date was set");
                errors.rejectValue("discontinued","computer.discontinued.error","Invalid discontinued date provided");
            }
        }

        if(obj.getCompanyId() != null && !obj.getCompanyId().equals(0)) {
            logger.debug("Param companyId detected. Value is: " + obj.getCompanyId());
            Long companyId = obj.getCompanyId();
            if(companyId < 0) {
                logger.info("Invalid company was set");
                errors.rejectValue("company","company.error","Invalid company provided");
            }
        }

    }


}
