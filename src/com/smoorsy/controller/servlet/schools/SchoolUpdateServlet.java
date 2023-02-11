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

import static com.smoorsy.utils.UrlPath.SCHOOLS_OF_DEPARTMENT;
import static com.smoorsy.utils.UrlPath.SCHOOLS_UPDATE;

@WebServlet(SCHOOLS_UPDATE)
public class SchoolUpdateServlet extends HttpServlet {
    private final SchoolService schoolService = SchoolService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SchoolDto schoolDto = SchoolDto.builder()
                .id(req.getParameter("id"))
                .department_id(req.getParameter("department_id"))
                .name(req.getParameter("name"))
                .manager_id(req.getParameter("manager_id"))
                .build();

        try {
            schoolService.update(schoolDto).ifPresentOrElse(
                    (school) -> {
                        try {
                            resp.sendRedirect(SCHOOLS_OF_DEPARTMENT);
                        } catch (IOException e) {
                            throw new RuntimeException();
                        }
                    },
                    () -> {
                        System.out.println("SchoolUpdateServlet : школа не получена, не удалось обновить");
                        resp.setStatus(500);
                    }
            );

        } catch (ValidationException e) {
            for (Error error : e.getErrors()) {
                System.out.println(error.getCode() + " : " + error.getMessage());
            }

            resp.sendRedirect(SCHOOLS_OF_DEPARTMENT + "?errorValidation");
        }
    }
}
