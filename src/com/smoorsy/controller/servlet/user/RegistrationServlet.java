package com.smoorsy.controller.servlet.user;

import com.smoorsy.model.dto.user.RegistrationUserDto;
import com.smoorsy.model.dto.user.UserDto;
import com.smoorsy.model.service.UserService;
import com.smoorsy.model.service.validator.Error;
import com.smoorsy.model.service.validator.exception.ValidationException;
import com.smoorsy.utils.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

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
        RegistrationUserDto registrationUserDto = RegistrationUserDto.builder()
                .surname(req.getParameter("surname"))
                .name(req.getParameter("name"))
                .patronymic(req.getParameter("patronymic"))
                .birthday(req.getParameter("birthday"))
                .email(req.getParameter("email"))
                .password(req.getParameter("password"))
                .build();

        try {
            // В сессию всегда кладём dto образа UserDto
            Optional<UserDto> userDto = userService.registration(registrationUserDto);
            req.getSession().setAttribute("USER", userDto.get());
            System.out.println("Регистрация - пользователь был сохранён и добавлен в сессию");
            resp.sendRedirect("/");
        } catch (ValidationException e) {
            System.out.println("Регистрация - валидация не прошла");
            for (Error error : e.getErrors()) {
                req.setAttribute(error.getCode(), error.getMessage());
            }
            doGet(req, resp);
        }
    }
}
