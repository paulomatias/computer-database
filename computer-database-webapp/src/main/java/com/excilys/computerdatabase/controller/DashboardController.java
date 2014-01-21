package com.excilys.computerdatabase.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.service.ComputerService;

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
    private static final String ATTR_COMPUTER_SEARCH = "searchString";
    private static final String ATTR_LOCALE = "locale";
    private static final String ATTR_SORT = "sort";

    private static final String PARAM_SUBMIT_ADD = "submitAdd";
    private static final String PARAM_SUBMIT_EDIT = "submitEdit";
    private static final String PARAM_SUBMIT_DELETE = "submitDelete";

    private static final String PARAM_PAGE = "page";
    private static final String PARAM_SEARCH = "search";
    private static final String PARAM_SORT = "sort";
    private static final String PARAM_DIRECTION = "dir";

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
                           @RequestParam(value = PARAM_SORT, required = false) String pSort,
                           @RequestParam(value = PARAM_DIRECTION, required = false) Sort.Direction pDirection,
                           HttpServletRequest request,
                           HttpSession session) {

        logger.debug("Entering doGet");

        int page=1;

        String search="";

        Sort sort = session.getAttribute(ATTR_SORT) == null ? new Sort(Sort.Direction.ASC,"name") : (Sort) session.getAttribute(ATTR_SORT) ;

        //Parameters handle
        if(pSubmitAdd != null && pSubmitAdd == true)
            model.addAttribute(ATTR_SUBMIT_ADD,true);
        if(pSubmitEdit != null && pSubmitEdit == true)
            model.addAttribute(ATTR_SUBMIT_EDIT,true);
        if(pSubmitDelete != null && pSubmitDelete == true)
            model.addAttribute(ATTR_SUBMIT_DELETE,true);
        if(pPage != null && pPage > 0)
            page = pPage;
        if(pSearch != null && !pSearch.trim().isEmpty())
            search = pSearch.trim();
        if(pSort != null && pDirection != null) {
            sort = new Sort(pDirection,pSort);
            session.setAttribute(ATTR_SORT,sort);
        }

        //Retrieve page
        Pageable pageRequest = new PageRequest(page-1, ITEMS_PER_PAGE, sort);

        Page<Computer> computerPage = computerService.retrievePage(pageRequest, search);

        model.addAttribute(ATTR_COMPUTER_PAGE, computerPage);
        model.addAttribute(ATTR_COMPUTER_SEARCH, search);

        session.setAttribute(ATTR_LOCALE, RequestContextUtils.getLocale(request));

        logger.debug("Leaving doGet");
        return "dashboard";
    }

}
