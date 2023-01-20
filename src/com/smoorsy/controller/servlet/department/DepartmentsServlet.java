package com.smoorsy.controller.servlet.department;

import com.smoorsy.model.service.DepartmentService;
import com.smoorsy.utils.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.smoorsy.utils.UrlPath.DEPARTMENTS;

@WebServlet(DEPARTMENTS)
public class DepartmentsServlet extends HttpServlet {

    private final DepartmentService departmentService = DepartmentService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().setAttribute("USER-departments", departmentService.findAll());
        req.getRequestDispatcher(JspHelper.getPath("departments")).forward(req, resp);
    }
}
