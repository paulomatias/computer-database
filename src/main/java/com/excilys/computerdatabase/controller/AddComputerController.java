package com.excilys.computerdatabase.controller;

import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.dto.ComputerDto;
import com.excilys.computerdatabase.mapper.ComputerMapper;
import com.excilys.computerdatabase.service.CompanyService;
import com.excilys.computerdatabase.service.ComputerService;
import com.excilys.computerdatabase.validator.ComputerValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Project: computer-database
 * Package: com.excilys.computerdatabase.controller
 * User: lortola
 * Date: 15/11/13
 * Description: N/A
 */
@Controller("/addComputer")
public class AddComputerController {

    /*
     * Attributes
     */
    private static final String PARAM_COMPUTER_NAME = "computer_name";
    private static final String PARAM_COMPUTER_INTRODUCED = "computer_introduced";
    private static final String PARAM_COMPUTER_DISCONTINUED = "computer_discontinued";
    private static final String PARAM_COMPUTER_COMPANY_ID = "company_id";

    private static final String ATTR_COMPUTER = "computer";
    private static final String ATTR_COMPANIES = "companies";
    private static final String ATTR_ERROR_BITS = "error_bits";

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    private static Logger logger = LoggerFactory.getLogger(AddComputerController.class);

    @Autowired
    private CompanyService companyService;
    @Autowired
    private ComputerService computerService;

    @Autowired
    private ComputerMapper computerMapper;

    /*
     * Constructurs
     */
    public AddComputerController() {
        super();
    }

    @RequestMapping(method = RequestMethod.GET)
    protected String doGet(Model model) {
        logger.debug("Entering doGet");

        model.addAttribute(ATTR_COMPANIES, companyService.retrieveAll());

        logger.debug("Leaving doGet");
        return "addComputer";
    }

    @RequestMapping(method = RequestMethod.POST)
    protected String doPost(Model model,
                           @RequestParam(value = PARAM_COMPUTER_NAME, required = false) String pComputerName,
                           @RequestParam(value = PARAM_COMPUTER_INTRODUCED, required = false) String pComputerIntroduced,
                           @RequestParam(value = PARAM_COMPUTER_DISCONTINUED, required = false) String pComputerDiscontinued,
                           @RequestParam(value = PARAM_COMPUTER_COMPANY_ID, required = false) Long pCompanyId) {
        logger.debug("Entering doPost");

        int errorBits = 0;

        //Build DTO
        ComputerDto cdto = ComputerDto.builder()
                .name(pComputerName)
                .introduced(pComputerIntroduced)
                .discontinued(pComputerDiscontinued)
                .companyId(pCompanyId).build();

        //Perform object validation
        errorBits = new ComputerValidator().validate(cdto);

        //Add computer if everything went good
        if(errorBits == 0) {
            Computer computer = computerMapper.fromDto(cdto);
            computerService.create(computer);
            if(computer.getId() == null)
                return "redirect:dashboard?submitAdd=false";
            else
                return "redirect:dashboard?submitAdd=true";
        }

        //Otherwise reload current page with previous content

        model.addAttribute(ATTR_COMPANIES, companyService.retrieveAll());
        model.addAttribute(ATTR_COMPUTER, cdto);
        model.addAttribute(ATTR_ERROR_BITS, errorBits);

        logger.debug("Leaving doPost");
        return "addComputer";
    }
}
