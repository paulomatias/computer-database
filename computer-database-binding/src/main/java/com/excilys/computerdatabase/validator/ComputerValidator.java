package com.excilys.computerdatabase.validator;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

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

    @Autowired
    @Qualifier(value = "messageSource")
    private ResourceBundleMessageSource messageSource;


    @Override
    public boolean supports(Class<?> aClass) {
        if(ComputerDto.class.equals(aClass))
            return true;
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {

        DateTimeFormatter dtf = DateTimeFormat.forPattern(messageSource.getMessage("form.date.pattern", null, LocaleContextHolder.getLocale()));

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
                dtf.parseDateTime(obj.getIntroduced());

            } catch (RuntimeException e) {
                logger.info("Invalid introduced date was set");
                errors.rejectValue("introduced","computer.introduced.error","Invalid introduced date provided");
            }
        }

        if(obj.getDiscontinued() != null && !obj.getDiscontinued().trim().isEmpty()) {
            try {
                logger.debug("Param discontinued detected. Value is: " + obj.getDiscontinued());
                dtf.parseDateTime(obj.getDiscontinued());
            } catch (RuntimeException e) {
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
