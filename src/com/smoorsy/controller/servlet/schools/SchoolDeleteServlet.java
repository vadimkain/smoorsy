package com.smoorsy.controller.servlet.schools;

import com.smoorsy.model.dto.school.SchoolDto;
import com.smoorsy.model.service.SchoolService;
import com.smoorsy.model.service.validator.Error;
import com.smoorsy.model.service.validator.exception.ValidationException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.smoorsy.utils.UrlPath.SCHOOLS_DELETE;
import static com.smoorsy.utils.UrlPath.SCHOOLS_OF_DEPARTMENT;

@WebServlet(SCHOOLS_DELETE)
public class SchoolDeleteServlet extends HttpServlet {

    private final SchoolService schoolService = SchoolService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SchoolDto departmentDto = SchoolDto.builder()
                .id(req.getParameter("id"))
                .build();

        try {
            if (schoolService.delete(departmentDto)) {
                System.out.println("SchoolDeleteServlet : школа удалена успешно");
                resp.sendRedirect(SCHOOLS_OF_DEPARTMENT);
            } else {
                System.out.println("SchoolDeleteServlet : школа не была удалена");
                resp.sendRedirect(SCHOOLS_OF_DEPARTMENT + "?errorFalse");
            }
        } catch (ValidationException e) {
            System.out.println("SchoolDeleteServlet : валидация была провалена");
            for (Error error : e.getErrors()) {
                System.out.println(error.getCode() + " : " + error.getMessage());
            }
        }
    }
}
