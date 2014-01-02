package com.excilys.computerdatabase.controller;

import com.excilys.computerdatabase.service.ComputerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Project: computer-database
 * Package: com.excilys.computerdatabase.controller
 * User: lortola
 * Date: 15/11/13
 * Description: N/A
 */
@Controller("/deleteComputer")
public class DeleteComputerController {

    /*
     * Attributes
     */
    private static final String PARAM_COMPUTER_IDS = "selection";
    private static Logger logger = LoggerFactory.getLogger(DeleteComputerController.class);

    @Autowired
    private ComputerService computerService;

    /*
     * Constructurs
     */
    public DeleteComputerController() {
        super();
    }

    @RequestMapping(method = RequestMethod.POST)
    protected String doPost(Model model, @RequestParam(value = PARAM_COMPUTER_IDS, required = true) String computerIds) {
        logger.debug("Entering doPost with ids " + computerIds);

        List<Long> computerIdsList = new ArrayList<Long>();

        for(String id : computerIds.split(",")) {
            if(id != null && !id.isEmpty())
                computerIdsList.add(Long.parseLong(id));
        }

        //If delete successful, sending result to dashboard
        if(computerService.delete(computerIdsList))
            return "redirect:dashboard?submitDelete=true";
        else
            return "redirect:dashboard?submitDelete=false";
    }
}
