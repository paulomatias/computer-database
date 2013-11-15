package com.excilys.computerdatabase.controller;

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
@WebServlet("/addComputer")
public class AddComputerController extends HttpServlet {

    public static final String TAG = AddComputerController.class.getSimpleName();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(TAG + ": Entering doGet");

        resp.sendRedirect("addComputer.jsp");

        System.out.println(TAG + ": Leaving doGet");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(TAG + ": Entering doPost");

        resp.sendRedirect("dashboard");

        System.out.println(TAG + ": Entering doPost");
    }
}
