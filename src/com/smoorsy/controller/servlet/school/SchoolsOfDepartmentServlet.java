package com.smoorsy.controller.servlet.school;

import com.smoorsy.model.service.SchoolService;
import com.smoorsy.model.service.validator.exception.ValidationException;
import com.smoorsy.utils.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.smoorsy.utils.UrlPath.SCHOOLS_OF_DEPARTMENT;

@WebServlet(SCHOOLS_OF_DEPARTMENT)
public class SchoolsOfDepartmentServlet extends HttpServlet {
    private final SchoolService schoolService = SchoolService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String department_id = req.getParameter("department-id");
        if (department_id == null) {
            req.getSession().setAttribute("schools", schoolService.findAll());
            req.getRequestDispatcher(JspHelper.getPath("schools")).forward(req, resp);
        } else {
            try {
                req.getSession().setAttribute("schools-of-department", schoolService.findAllByDepartment(department_id));
                req.getRequestDispatcher(JspHelper.getPath("schools")).forward(req, resp);
            } catch (ValidationException e) {
                System.out.println("SchoolsOfDepartmentServlet : " + e.getErrors());
//                req.getSession().setAttribute("schools-of-department-ERROR", e.getErrors());
                resp.sendRedirect(SCHOOLS_OF_DEPARTMENT);
            }
        }
    }
}