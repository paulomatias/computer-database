package com.excilys.computerdatabase.controller;

import com.excilys.computerdatabase.common.Page;
import com.excilys.computerdatabase.domain.Computer;
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


    public static final int ITEMS_PER_PAGE = 20;

    private static final String ATTR_SUBMIT_ADD = "submitAdd";
    private static final String ATTR_SUBMIT_EDIT = "submitEdit";
    private static final String ATTR_SUBMIT_DELETE = "submitDelete";
    private static final String ATTR_COMPUTER_PAGE = "computerPage";

    private static final String PARAM_SUBMIT_ADD = "submitAdd";
    private static final String PARAM_SUBMIT_EDIT = "submitEdit";
    private static final String PARAM_SUBMIT_DELETE = "submitDelete";

    private static final String PARAM_PAGE = "page";
    private static final String PARAM_SEARCH = "search";
    private static final String PARAM_SORT = "sort";


    private static Logger logger = LoggerFactory.getLogger(DashboardController.class);

    private ComputerService computerService;

    public DashboardController() {
        super();
        computerService = ServiceFactory.INSTANCE.getComputerService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("Entering doGet");

        //Default values
        int currentPage = 1;
        String searchString = null;
        int sort = 0;

        //Parameters handle
        if(req.getParameter(PARAM_SUBMIT_ADD) != null && "true".equals(req.getParameter(PARAM_SUBMIT_ADD)))
            req.setAttribute(ATTR_SUBMIT_ADD,true);
        if(req.getParameter(PARAM_SUBMIT_EDIT) != null && "true".equals(req.getParameter(PARAM_SUBMIT_EDIT)))
            req.setAttribute(ATTR_SUBMIT_EDIT,true);
        if(req.getParameter(PARAM_SUBMIT_DELETE) != null && "true".equals(req.getParameter(PARAM_SUBMIT_DELETE)))
            req.setAttribute(ATTR_SUBMIT_DELETE,true);
        if(req.getParameter(PARAM_PAGE) != null) {
            int tmpPage = Integer.parseInt(req.getParameter(PARAM_PAGE));
            if(tmpPage >= 1)
                currentPage = tmpPage;
        }
        if(req.getParameter(PARAM_SEARCH) != null && !req.getParameter(PARAM_SEARCH).trim().isEmpty())
            searchString = req.getParameter(PARAM_SEARCH).trim();
        if(req.getParameter(PARAM_SORT) != null && !req.getParameter(PARAM_SORT).trim().isEmpty() && !req.getParameter(PARAM_SORT).equals("0"))
            sort = Integer.parseInt(req.getParameter(PARAM_SORT));

        //Retrieve page
        Page<Computer> computerPage = computerService.retrievePage((currentPage-1)*ITEMS_PER_PAGE,ITEMS_PER_PAGE,searchString,sort);

        req.setAttribute(ATTR_COMPUTER_PAGE,computerPage);

        RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/dashboard.jsp");

        dispatcher.forward(req,resp);

        logger.debug("Leaving doGet");
    }

}
