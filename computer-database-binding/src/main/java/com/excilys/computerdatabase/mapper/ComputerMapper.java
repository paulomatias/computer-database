package com.excilys.computerdatabase.mapper;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.dto.ComputerDto;
import com.excilys.computerdatabase.service.CompanyService;

/**
 * Project: computer-database
 * Package: com.excilys.computerdatabase.mapper
 * User: lortola
 * Date: 03/12/13
 * Description: N/A
 */
@Component
public class ComputerMapper {

    private static Logger logger = LoggerFactory.getLogger(ComputerMapper.class);

    @Autowired
    private CompanyService companyService;

    @Autowired
    @Qualifier(value = "messageSource")
    private ResourceBundleMessageSource messageSource;

    public Computer fromDto(ComputerDto dto) {
    	if(dto == null)
            return null;
    	logger.debug("Entering fromDto with ComputerDto " + dto.toString());
        
        DateTimeFormatter dtf = DateTimeFormat.forPattern(messageSource.getMessage("form.date.pattern",null,LocaleContextHolder.getLocale()));

        Computer.Builder cb = Computer.builder();

        cb.id(dto.getId()).name(dto.getName());

        if(dto.getCompanyId() != null && !dto.getCompanyId().equals(0L))
            cb.company(companyService.retrieve(dto.getCompanyId()));

        if(dto.getIntroduced() != null && !dto.getIntroduced().isEmpty())
            cb.introduced(dtf.parseDateTime(dto.getIntroduced()));

        if(dto.getDiscontinued() != null && !dto.getDiscontinued().isEmpty())
            cb.discontinued(dtf.parseDateTime(dto.getDiscontinued()));

        logger.debug("leaving fromDto with object built:" + cb.build());
        return cb.build();
    }

    public ComputerDto toDto(Computer obj) {
        logger.debug("Entering toDto");
        if(obj == null)
            return null;

        DateTimeFormatter dtf = DateTimeFormat.forPattern(messageSource.getMessage("form.date.pattern",null,LocaleContextHolder.getLocale()));

        ComputerDto.Builder cdtob = ComputerDto.builder();

        cdtob.id(obj.getId()).name(obj.getName());

        if(obj.getIntroduced() != null)
            cdtob.introduced(obj.getIntroduced().toString(dtf));

        if(obj.getDiscontinued() != null)
            cdtob.discontinued(obj.getDiscontinued().toString(dtf));

        if(obj.getCompany() != null)
            cdtob.companyId(obj.getCompany().getId());

        logger.debug("Leaving toDto with object built:" + cdtob.build());
        return cdtob.build();

    }
}
