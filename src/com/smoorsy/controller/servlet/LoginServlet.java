package com.smoorsy.controller.servlet;

import com.smoorsy.model.dto.LoginUserDto;
import com.smoorsy.model.dto.UserDto;
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

import static com.smoorsy.utils.UrlPath.LOGIN;

@WebServlet(LOGIN)
public class LoginServlet extends HttpServlet {

    private final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspHelper.getPath("mainpage")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LoginUserDto loginUserDto = LoginUserDto.builder()
                .email(req.getParameter("email"))
                .password(req.getParameter("password"))
                .build();

        try {
            userService.login(loginUserDto).ifPresentOrElse(
                    (userDto) -> {
                        onLoginSuccess(userDto, req, resp);
                        System.out.println("Авторизация - все прошло успешно");
                    },
                    () -> {
                        System.out.println("Авторизация - неверно введены данные или пользователь не существует");
                        onLoginFail(req, resp);
                    }
            );
        } catch (ValidationException e) {
            System.out.println("Авторизация - валидация не прошла");
            for (Error error : e.getErrors()) {
                System.out.println(error.getCode() + " : " + error.getMessage());
                req.setAttribute(error.getCode(), error.getMessage());
            }
            doGet(req, resp);
        }
    }

    private void onLoginSuccess(UserDto userDto, HttpServletRequest req, HttpServletResponse resp) {

        req.getSession().setAttribute("USER", userDto);

        try {
            resp.sendRedirect("/");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void onLoginFail(HttpServletRequest req, HttpServletResponse resp) {
        try {
//            resp.sendRedirect("/?error&email=" + req.getParameter("email"));
            req.setAttribute("error.email", req.getParameter("email"));
            doGet(req, resp);
        } catch (IOException | ServletException e) {
            throw new RuntimeException(e);
        }
    }
}
