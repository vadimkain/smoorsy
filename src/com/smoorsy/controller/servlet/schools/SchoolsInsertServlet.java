package com.smoorsy.controller.servlet.schools;

import com.smoorsy.model.dto.school.SchoolInsertDto;
import com.smoorsy.model.service.SchoolService;
import com.smoorsy.model.service.validator.Error;
import com.smoorsy.model.service.validator.exception.ValidationException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.smoorsy.utils.UrlPath.*;

@WebServlet(SCHOOLS_INSERT)
public class SchoolsInsertServlet extends HttpServlet {
    private final SchoolService schoolService = SchoolService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SchoolInsertDto schoolInsertDto = SchoolInsertDto.builder()
                .department_id(req.getParameter("department_id"))
                .name(req.getParameter("name"))
                .manager_id(req.getParameter("manager_id"))
                .build();

        try {
            schoolService.insert(schoolInsertDto).ifPresentOrElse(
                    (school) -> {
                        try {
                            resp.sendRedirect(SCHOOLS_OF_DEPARTMENT);
                        } catch (IOException e) {
                            throw new RuntimeException();
                        }
                    },
                    () -> {
                        System.out.println("SchoolsInsertServlet : школа не получена, не удалось добавить");
                        resp.setStatus(500);
                    }
            );

        } catch (ValidationException e) {
            for (Error error : e.getErrors()) {
                System.out.println(error.getCode() + " : " + error.getMessage());
            }

            resp.sendRedirect(DEPARTMENTS + "?errorValidation");
        }
    }
}
