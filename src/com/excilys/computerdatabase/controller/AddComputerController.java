package com.excilys.computerdatabase.controller;

import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.dto.ComputerDto;
import com.excilys.computerdatabase.mapper.ComputerMapper;
import com.excilys.computerdatabase.service.CompanyService;
import com.excilys.computerdatabase.service.ComputerService;
import com.excilys.computerdatabase.service.factory.ServiceFactory;
import com.excilys.computerdatabase.validator.ComputerValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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
@WebServlet("/addComputer")
public class AddComputerController extends HttpServlet {

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

    private CompanyService companyService;
    private ComputerService computerService;

    /*
     * Constructurs
     */
    public AddComputerController() {
        super();
        companyService = ServiceFactory.INSTANCE.getCompanyService();
        computerService = ServiceFactory.INSTANCE.getComputerService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("Entering doGet");

        RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/addComputer.jsp");

        req.setAttribute(ATTR_COMPANIES, companyService.retrieveAll());

        logger.debug("Leaving doGet");
        dispatcher.forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("Entering doPost");

        int errorBits = 0;
        RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/addComputer.jsp");

        //Build DTO
        ComputerDto cdto = ComputerDto.builder()
                .name(req.getParameter(PARAM_COMPUTER_NAME))
                .introduced(req.getParameter(PARAM_COMPUTER_INTRODUCED))
                .discontinued(req.getParameter(PARAM_COMPUTER_DISCONTINUED))
                .companyId(Long.parseLong(req.getParameter(PARAM_COMPUTER_COMPANY_ID))).build();

        //Perform object validation
        errorBits = new ComputerValidator().validate(cdto);

        //Add computer if everything went good
        if(errorBits == 0) {
            Computer computer = ComputerMapper.fromDto(cdto);
            computerService.create(computer);
            if(computer.getId() == null)
                resp.sendRedirect("dashboard?submitAdd=false");
            else
                resp.sendRedirect("dashboard?submitAdd=true");
            return;
        }

        //Otherwise reload current page with previous content

        req.setAttribute(ATTR_COMPANIES, companyService.retrieveAll());
        req.setAttribute(ATTR_COMPUTER,cdto);
        req.setAttribute(ATTR_ERROR_BITS,errorBits);

        logger.debug("Leaving doPost");
        dispatcher.forward(req,resp);
    }
}
