package com.excilys.computerdatabase.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created with IntelliJ IDEA.
 * User: lortola
 * Date: 10/01/14
 * Time: 17:18
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/error")
public class ErrorController {

    @RequestMapping("/404")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handle404(Exception e) {
        return new ModelAndView("404");

    }
    @RequestMapping("/403")
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ModelAndView handle403(Exception e) {
        return new ModelAndView("403");
    }
    @RequestMapping("/500")
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handle500(Exception e) {
        return new ModelAndView("500");
    }

    @RequestMapping("/exception")
    public ModelAndView handleException(Exception e) {
        ModelAndView mav = new ModelAndView("500");

        mav.addObject("exception",e.getMessage());

        return mav;
    }



}
