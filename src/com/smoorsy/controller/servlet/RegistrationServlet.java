package com.smoorsy.controller.servlet;

import com.smoorsy.model.dto.RegistrationUserDto;
import com.smoorsy.model.service.UserService;
import com.smoorsy.model.service.validator.exception.ValidationException;
import com.smoorsy.utils.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.smoorsy.utils.UrlPath.REGISTRATION;

@WebServlet(REGISTRATION)
public class RegistrationServlet extends HttpServlet {
    UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspHelper.getPath("mainpage")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RegistrationUserDto userDto = RegistrationUserDto.builder()
                .surname(req.getParameter("surname"))
                .name(req.getParameter("name"))
                .patronymic(req.getParameter("patronymic"))
                .birthday(req.getParameter("birthday"))
                .email(req.getParameter("email"))
                .password(req.getParameter("password"))
                .build();

        try {
            userService.registration(userDto);
            req.getSession().setAttribute("USER", userDto);
            System.out.println("Отработало все хорошо");
            resp.sendRedirect("/");
        } catch (ValidationException e) {
            System.out.println("Произошла жопа");
            req.setAttribute("errors", e.getErrors());
            doGet(req, resp);
        }
    }
}
