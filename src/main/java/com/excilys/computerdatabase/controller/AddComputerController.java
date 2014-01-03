package com.excilys.computerdatabase.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.dto.ComputerDto;
import com.excilys.computerdatabase.mapper.ComputerMapper;
import com.excilys.computerdatabase.service.CompanyService;
import com.excilys.computerdatabase.service.ComputerService;
import com.excilys.computerdatabase.validator.ComputerValidator;

/**
 * Project: computer-database
 * Package: com.excilys.computerdatabase.controller
 * User: lortola
 * Date: 15/11/13
 * Description: N/A
 */
@Controller
@RequestMapping("/addComputer")
public class AddComputerController {

    /*
     * Attributes
     */
    private static final String ATTR_COMPUTER = "computer";
    private static final String ATTR_COMPANIES = "companies";

    private static Logger logger = LoggerFactory.getLogger(AddComputerController.class);

    @Autowired
    private CompanyService companyService;
    @Autowired
    private ComputerService computerService;

    @Autowired
    private ComputerMapper computerMapper;

    @Autowired
    private ComputerValidator computerValidator;

    /*
     * Constructurs
     */
    public AddComputerController() {
        super();
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(computerValidator);
    }

    @RequestMapping(method = RequestMethod.GET)
    protected String doGet(Model model) {
        logger.debug("Entering doGet");
        
        model.addAttribute(ATTR_COMPANIES, companyService.retrieveAll());
        model.addAttribute(ATTR_COMPUTER, new ComputerDto());

        logger.debug("Leaving doGet");

        return "addComputer";
    }

    @RequestMapping(method = RequestMethod.POST)
    protected String doPost(Model model, @Valid @ModelAttribute(ATTR_COMPUTER) ComputerDto computerDto, BindingResult result) {
        logger.debug("Entering doPost");

        //Add computer if everything went good
        if(!result.hasErrors()) {
            Computer computer = computerMapper.fromDto(computerDto);
            computerService.create(computer);
            if(computer.getId() == null)
                return "redirect:dashboard?submitAdd=false";
            else
                return "redirect:dashboard?submitAdd=true";
        }

        //Otherwise reload current page with previous content

        model.addAttribute(ATTR_COMPANIES, companyService.retrieveAll());
        
        logger.debug("Leaving doPost");
        
        return "addComputer";
    }
}
