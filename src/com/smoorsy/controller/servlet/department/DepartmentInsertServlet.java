package com.smoorsy.controller.servlet.department;

import com.smoorsy.model.dto.department.DepartmentDto;
import com.smoorsy.model.dto.department.DepartmentInsertDto;
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
import static com.smoorsy.utils.UrlPath.DEPARTMENTS_INSERT;

@WebServlet(DEPARTMENTS_INSERT)
public class DepartmentInsertServlet extends HttpServlet {
    private final DepartmentService departmentService = DepartmentService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DepartmentInsertDto departmentInsertDto = DepartmentInsertDto.builder()
                .city(req.getParameter("city"))
                .build();

        try {
            departmentService.insert(departmentInsertDto).ifPresentOrElse(
                    (department) -> {
                        try {
                            resp.sendRedirect(DEPARTMENTS);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    },
                    () -> {
                        System.out.println("DepartmentInsertServlet : департамент не получен, не удалось добавить");
                        resp.setStatus(500);
                    }
            );

        } catch (ValidationException e) {
            for (Error error : e.getErrors()) {
                System.out.println(error.getCode() + " : " + error.getMessage());
            }

            // TODO: реализовать оповещение об ошибке на стороне фронтенда
            resp.sendRedirect(DEPARTMENTS + "?errorValidation");
        }
    }
}
