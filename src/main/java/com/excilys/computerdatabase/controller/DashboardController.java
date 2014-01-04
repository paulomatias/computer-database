package com.excilys.computerdatabase.controller;

import com.excilys.computerdatabase.common.Page;
import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.service.ComputerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Project: computer-database
 * Package: com.excilys.computerdatabase.controller
 * User: lortola
 * Date: 15/11/13
 * Description: N/A
 */
@Controller("/")
@RequestMapping(value={"/", "/dashboard"})
public class DashboardController {


    public static final int ITEMS_PER_PAGE = 20;

    private static final String ATTR_SUBMIT_ADD = "submitAdd";
    private static final String ATTR_SUBMIT_EDIT = "submitEdit";
    private static final String ATTR_SUBMIT_DELETE = "submitDelete";
    private static final String ATTR_COMPUTER_PAGE = "computerPage";
    private static final String ATTR_LOCALE = "locale";

    private static final String PARAM_SUBMIT_ADD = "submitAdd";
    private static final String PARAM_SUBMIT_EDIT = "submitEdit";
    private static final String PARAM_SUBMIT_DELETE = "submitDelete";

    private static final String PARAM_PAGE = "page";
    private static final String PARAM_SEARCH = "search";
    private static final String PARAM_SORT = "sort";

    private static Logger logger = LoggerFactory.getLogger(DashboardController.class);

    @Autowired
    private ComputerService computerService;

    public DashboardController() {
        super();
    }

    @RequestMapping(method = RequestMethod.GET)
    protected String doGet(Model model,
                           @RequestParam(value = PARAM_SUBMIT_ADD, required = false) Boolean pSubmitAdd,
                           @RequestParam(value = PARAM_SUBMIT_EDIT, required = false) Boolean pSubmitEdit,
                           @RequestParam(value = PARAM_SUBMIT_DELETE, required = false) Boolean pSubmitDelete,
                           @RequestParam(value = PARAM_PAGE, required = false, defaultValue = "1") Integer pPage,
                           @RequestParam(value = PARAM_SEARCH, required = false, defaultValue = "") String pSearch,
                           @RequestParam(value = PARAM_SORT, required = false, defaultValue = "0") Integer pSort,
                           HttpServletRequest request,
                           HttpSession session) {

        logger.debug("Entering doGet");

        int page=1;
        int sort=0;
        String search="";

        //Parameters handle
        if(pSubmitAdd != null && pSubmitAdd == true)
            model.addAttribute(ATTR_SUBMIT_ADD,true);
        if(pSubmitEdit != null && pSubmitEdit == true)
            model.addAttribute(ATTR_SUBMIT_EDIT,true);
        if(pSubmitDelete != null && pSubmitDelete == true)
            model.addAttribute(ATTR_SUBMIT_DELETE,true);
        if(pPage != null && pPage > 1)
            page = pPage;

        if(pSearch != null && !pSearch.trim().isEmpty())
            search = pSearch.trim();

        if(pSort != null && pSort > 0)
            sort = pSort;

        //Retrieve page
        Page<Computer> computerPage = computerService.retrievePage((page-1)*ITEMS_PER_PAGE,ITEMS_PER_PAGE,search,sort);

        model.addAttribute(ATTR_COMPUTER_PAGE, computerPage);
        session.setAttribute(ATTR_LOCALE, RequestContextUtils.getLocale(request));

        logger.debug("Leaving doGet");
        return "dashboard";
    }

}
