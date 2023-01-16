package com.smoorsy.controller.filter;

import com.smoorsy.model.dto.UserDto;
import com.smoorsy.model.entity.users_schema.Role;
import com.smoorsy.model.entity.users_schema.Users_and_Roles;
import com.smoorsy.model.service.UserService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.smoorsy.utils.UrlPath.*;

@WebFilter("/*")
public class AuthorizationFilter implements Filter {
    private static final Set<String> UNKNOWN = Set.of(MAIN_PAGE, REGISTRATION, LOGIN);
    private static final Set<String> LEARNER = Set.of(MAIN_PAGE, LOGOUT);
    private static final Set<String> TEACHER = Set.of(MAIN_PAGE, LOGOUT);
    private static final Set<String> CLASSROOM_TEACHER = Set.of(MAIN_PAGE, LOGOUT);
    private static final Set<String> MANAGER = Set.of(MAIN_PAGE, LOGOUT);
    private static final Set<String> DEVELOPER = Set.of(MAIN_PAGE, LOGOUT, DEPARTMENTS);

    private final UserService userService = UserService.getInstance();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String requestURI = ((HttpServletRequest) servletRequest).getRequestURI();

        boolean accessToUNKNOWN =
                UNKNOWN.stream()
                        .anyMatch(requestURI::equals)
                &&
                !isUserLoggedIn(servletRequest);

        boolean accessToLEARNER =
                LEARNER.stream()
                        .anyMatch(requestURI::equals)
                &&
                roles(servletRequest).stream()
                        .anyMatch(usersAndRoles -> usersAndRoles.getRole().equals(Role.LEARNER));

        boolean accessToTEACHER =
                TEACHER.stream()
                        .anyMatch(requestURI::equals)
                &&
                roles(servletRequest).stream()
                        .anyMatch(usersAndRoles -> usersAndRoles.getRole().equals(Role.TEACHER));

        boolean accessToCLASSROOM_TEACHER =
                CLASSROOM_TEACHER.stream()
                        .anyMatch(requestURI::equals)
                &&
                roles(servletRequest).stream()
                        .anyMatch(usersAndRoles -> usersAndRoles.getRole().equals(Role.CLASSROOM_TEACHER));

        boolean accessToMANAGER =
                MANAGER.stream()
                        .anyMatch(requestURI::equals)
                &&
                roles(servletRequest).stream()
                        .anyMatch(usersAndRoles -> usersAndRoles.getRole().equals(Role.MANAGER));

        boolean accessToDEVELOPER =
                DEVELOPER.stream()
                        .anyMatch(requestURI::equals)
                &&
                roles(servletRequest).stream()
                        .anyMatch(usersAndRoles -> usersAndRoles.getRole().equals(Role.DEVELOPER));

//        Загружаем статику независимо от роли пользователя
        if (requestURI.startsWith(GET_STATIC_CONTENT)) filterChain.doFilter(servletRequest, servletResponse);

        if (accessToUNKNOWN) filterChain.doFilter(servletRequest, servletResponse);
        else if (accessToLEARNER) filterChain.doFilter(servletRequest, servletResponse);
        else if (accessToTEACHER) filterChain.doFilter(servletRequest, servletResponse);
        else if (accessToCLASSROOM_TEACHER) filterChain.doFilter(servletRequest, servletResponse);
        else if (accessToMANAGER) filterChain.doFilter(servletRequest, servletResponse);
        else if (accessToDEVELOPER) filterChain.doFilter(servletRequest, servletResponse);
        else {
            ((HttpServletResponse) servletResponse).sendRedirect(MAIN_PAGE);
        }
    }

    // Проверяем, авторизирован ли пользователь
    private boolean isUserLoggedIn(ServletRequest servletRequest) {
        UserDto user = (UserDto) ((HttpServletRequest) servletRequest).getSession().getAttribute("USER");
        return user != null;
    }

    // Берём список ролей из авторизированного пользователя
    private List<Users_and_Roles> roles(ServletRequest servletRequest) {
        if (isUserLoggedIn(servletRequest)) {
            UserDto user = (UserDto) ((HttpServletRequest) servletRequest).getSession().getAttribute("USER");

            List<Users_and_Roles> roles = userService.checkRoles(user);

            ((HttpServletRequest) servletRequest).getSession().setAttribute("USER-roles", roles);

            return roles;
        }
        return new ArrayList<>();
    }
}