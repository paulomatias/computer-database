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
import org.springframework.web.bind.annotation.RequestParam;

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
@RequestMapping("/editComputer")
public class EditComputerController {

    /*
     * Attributes
     */
    private static final String PARAM_COMPUTER_ID = "id";

    private static final String ATTR_COMPUTER = "computer";
    private static final String ATTR_COMPANIES = "companies";

    private static Logger logger = LoggerFactory.getLogger(EditComputerController.class);

    @Autowired
    private ComputerMapper computerMapper;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private ComputerService computerService;

    @Autowired
    private ComputerValidator computerValidator;

    /*
     * Constructurs
     */
    public EditComputerController() {
        super();
    }
    
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(computerValidator);
    }

    @RequestMapping(method = RequestMethod.GET)
    protected String doGet(@RequestParam(value=PARAM_COMPUTER_ID, required = true) Long pComputerId, Model model) {
        
    	logger.debug("Entering doGet");
        

    	model.addAttribute(ATTR_COMPANIES,companyService.retrieveAll());
    	model.addAttribute(ATTR_COMPUTER, computerMapper.toDto(computerService.retrieve(pComputerId)));

        logger.debug("Leaving doGet");

        return "editComputer";
    }

    @RequestMapping(method = RequestMethod.POST)
    protected String doPost(Model model, @ModelAttribute(ATTR_COMPUTER) @Valid ComputerDto computerDto, BindingResult result) {
        logger.debug("Entering doPost");

        //Add computer if everything went good
        if(!result.hasErrors()) {
            Computer computer = computerMapper.fromDto(computerDto);
            if(computerService.update(computer))
                return "redirect:dashboard?submitEdit=true";
            else
                return "redirect:dashboard?submitEdit=false";
        }

        //Otherwise reload current page with previous content

        model.addAttribute(ATTR_COMPANIES, companyService.retrieveAll());

        logger.debug("Leaving doPost");

        return "editComputer";
    }
}
