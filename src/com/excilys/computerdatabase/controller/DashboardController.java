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

/**
 * Project: computer-database
 * Package: com.excilys.computerdatabase.controller
 * User: lortola
 * Date: 15/11/13
 * Description: N/A
 */
@WebServlet("/dashboard")
public class DashboardController extends HttpServlet {


    private static final String ATTR_SUBMIT_ADD = "submitAdd";
    private static final String ATTR_SUBMIT_EDIT = "submitEdit";
    private static final String ATTR_SUBMIT_DELETE = "submitDelete";
    private static final String ATTR_COMPUTER_PAGE = "computerPage";

    private static Logger logger = LoggerFactory.getLogger(DashboardController.class);

    private ComputerService computerService;

    public DashboardController() {
        super();
        computerService = ServiceFactory.INSTANCE.getComputerService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("Entering doGet");

        if(req.getParameter("submitAdd") != null && "true".equals(req.getParameter("submitAdd")))
            req.setAttribute(ATTR_SUBMIT_ADD,true);
        if(req.getParameter("submitEdit") != null && "true".equals(req.getParameter("submitEdit")))
            req.setAttribute(ATTR_SUBMIT_EDIT,true);
        if(req.getParameter("submitDelete") != null && "true".equals(req.getParameter("submitDelete")))
            req.setAttribute(ATTR_SUBMIT_DELETE,true);

        req.setAttribute(ATTR_COMPUTER_PAGE,computerService.retrieveAll());

        RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/dashboard.jsp");

        dispatcher.forward(req,resp);

        logger.debug("Leaving doGet");
    }

}
