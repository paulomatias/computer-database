package com.excilys.computerdatabase.mapper;

import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.dto.ComputerDto;
import com.excilys.computerdatabase.service.CompanyService;
import com.excilys.computerdatabase.service.factory.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Project: computer-database
 * Package: com.excilys.computerdatabase.mapper
 * User: lortola
 * Date: 03/12/13
 * Description: N/A
 */
public class ComputerMapper {

    private static Logger logger = LoggerFactory.getLogger(ComputerMapper.class);
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    private static final CompanyService companyService = ServiceFactory.INSTANCE.getCompanyService();

    public static Computer fromDto(ComputerDto dto) {

        if(dto == null)
            return null;

        Computer.Builder cb = Computer.builder();

        cb.id(dto.getId()).name(dto.getName());

        if(dto.getCompanyId() != null && !dto.getCompanyId().equals(0L))
            cb.company(companyService.retrieve(dto.getCompanyId()));

        if(dto.getIntroduced() != null && !dto.getIntroduced().isEmpty())
            try {
                cb.introduced(DATE_FORMAT.parse(dto.getIntroduced()));
            } catch (ParseException e) {
                logger.warn("Cannot map object from DTO: " + e.getMessage());
            }

        if(dto.getDiscontinued() != null && !dto.getDiscontinued().isEmpty())
            try {
                cb.discontinued(DATE_FORMAT.parse(dto.getDiscontinued()));
            } catch (ParseException e) {
                logger.warn("Cannot map object from DTO: " + e.getMessage());
            }

            logger.debug("object built:" + cb.build());
        return cb.build();
    }

    public static ComputerDto toDto(Computer obj) {

        if(obj == null)
            return null;

        ComputerDto.Builder cdtob = ComputerDto.builder();

        cdtob.id(obj.getId()).name(obj.getName());

        if(obj.getIntroduced() != null)
            cdtob.introduced(DATE_FORMAT.format(obj.getIntroduced().getTime()));

        if(obj.getDiscontinued() != null)
            cdtob.discontinued(DATE_FORMAT.format(obj.getDiscontinued().getTime()));

        if(obj.getCompany() != null)
            cdtob.companyId(obj.getCompany().getId());

        return cdtob.build();

    }
}
