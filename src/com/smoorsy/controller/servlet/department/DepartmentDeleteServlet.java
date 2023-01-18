package com.smoorsy.controller.servlet.department;

import com.smoorsy.model.dto.department.DepartmentDto;
import com.smoorsy.model.service.DepartmentService;
import com.smoorsy.model.service.validator.Error;
import com.smoorsy.model.service.validator.exception.ValidationException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.smoorsy.utils.UrlPath.DEPARTMENTS;
import static com.smoorsy.utils.UrlPath.DEPARTMENTS_DELETE;

@WebServlet(DEPARTMENTS_DELETE)
public class DepartmentDeleteServlet extends HttpServlet {
    private final DepartmentService departmentService = DepartmentService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DepartmentDto departmentDto = DepartmentDto.builder()
                .id(req.getParameter("id"))
                .city(req.getParameter("city"))
                .build();

        try {
            if (departmentService.delete(departmentDto)) {
                System.out.println("DepartmentDeleteServlet : департамент удалён успешно");
                resp.sendRedirect(DEPARTMENTS);
            } else {
                System.out.println("DepartmentDeleteServlet : департамент не был удалён");
                resp.sendRedirect(DEPARTMENTS + "?errorFalse");
            }
        } catch (ValidationException e) {
            System.out.println("DepartmentDeleteServlet : валидация была провалена");
            for (Error error : e.getErrors()) {
                System.out.println(error.getCode() + " : " + error.getMessage());
            }
        }
    }
}
