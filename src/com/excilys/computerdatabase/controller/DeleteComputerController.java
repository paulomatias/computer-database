package com.excilys.computerdatabase.controller;

import com.excilys.computerdatabase.persistence.ComputerDao;
import com.excilys.computerdatabase.persistence.factory.DaoFactory;
import com.excilys.computerdatabase.service.ComputerService;
import com.excilys.computerdatabase.service.factory.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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
@WebServlet("/deleteComputer")
public class DeleteComputerController extends HttpServlet {

    /*
     * Attributes
     */
    private static final String PARAM_COMPUTER_IDS = "selection";
    private static Logger logger = LoggerFactory.getLogger(DeleteComputerController.class);
    private ComputerService computerService;



    /*
     * Constructurs
     */
    public DeleteComputerController() {
        super();
        computerService = ServiceFactory.INSTANCE.getComputerService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("Entering doGet");
        logger.warn("Not supposed to be in doGet!");
        logger.debug("Leaving doGet");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("Entering doPost");

        RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/editComputer.jsp");

        String[] selection = null;
        List<Long> computerIds = new ArrayList<Long>();

        if(req.getParameter(PARAM_COMPUTER_IDS) != null)
            selection = req.getParameter(PARAM_COMPUTER_IDS).split(",");

        for(String id : selection) {
            if(id != null && !id.isEmpty())
                computerIds.add(Long.parseLong(id));
        }

        //If delete successful, sending result to dashboard
        if(computerService.delete(computerIds))
            resp.sendRedirect("dashboard?submitDelete=true");
        else
            resp.sendRedirect("dashboard?submitDelete=false");
    }
}
