package com.smoorsy.utils;

import lombok.experimental.UtilityClass;

import java.util.Set;

@UtilityClass
public class UrlPath {
    public static final String GET_STATIC_CONTENT = "/static";
    public static final String MAIN_PAGE = "";
    public static final String REGISTRATION = "/registration";
    public static final String LOGIN = "/login";
    public static final String LOGOUT = "/logout";

    public static final Set<String> PUBLIC = Set.of(GET_STATIC_CONTENT, MAIN_PAGE, REGISTRATION, LOGIN);
    public static final Set<String> LEARNER = Set.of(MAIN_PAGE, LOGOUT);
    public static final Set<String> TEACHER = Set.of(MAIN_PAGE, LOGOUT);
    public static final Set<String> CLASSROOM_TEACHER = Set.of(MAIN_PAGE, LOGOUT);
    public static final Set<String> MANAGER = Set.of(MAIN_PAGE, LOGOUT);
}
