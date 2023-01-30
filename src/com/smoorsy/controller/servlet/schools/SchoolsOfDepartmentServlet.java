package com.smoorsy.controller.servlet.schools;

import com.smoorsy.model.dao.roles_schema.ManagerDao;
import com.smoorsy.model.dto.school.SchoolDto;
import com.smoorsy.model.entity.organization_schema.School;
import com.smoorsy.model.service.SchoolService;
import com.smoorsy.model.service.validator.exception.ValidationException;
import com.smoorsy.utils.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import static com.smoorsy.utils.UrlPath.SCHOOLS_OF_DEPARTMENT;

@WebServlet(SCHOOLS_OF_DEPARTMENT)
public class SchoolsOfDepartmentServlet extends HttpServlet {
    private final SchoolService schoolService = SchoolService.getInstance();
    private final ManagerDao managerDao = ManagerDao.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String department_id = req.getParameter("department-id");

        SchoolDto schoolDto = SchoolDto.builder()
                .id(req.getParameter("id"))
                .department_id(req.getParameter("department_id"))
                .name(req.getParameter("name"))
                .manager_id(req.getParameter("manager_id"))
                .build();

        req.getSession().setAttribute("managers", managerDao.findAll());
        if (department_id == null) {
            req.getSession().setAttribute("schools", schoolService.findAll());
            req.getRequestDispatcher(JspHelper.getPath("schools")).forward(req, resp);
        } else {
            try {
                List<School> allByDepartment = schoolService.findAllByDepartment(department_id);
                System.out.println(allByDepartment);
                req.getSession().setAttribute("schools-of-department", allByDepartment);
                req.getRequestDispatcher(JspHelper.getPath("schools")).forward(req, resp);
            } catch (ValidationException e) {
                System.out.println("SchoolsOfDepartmentServlet : " + e.getErrors());
//                req.getSession().setAttribute("schools-of-department-ERROR", e.getErrors());
                resp.sendRedirect(SCHOOLS_OF_DEPARTMENT);
            }
        }
    }
}