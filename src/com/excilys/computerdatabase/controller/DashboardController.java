package com.excilys.computerdatabase.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
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

    public static final String TAG = DashboardController.class.getSimpleName();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(TAG + ": Entering doGet");

        resp.sendRedirect("dashboard.jsp");

        System.out.println(TAG + ": Leaving doGet");
    }

}
