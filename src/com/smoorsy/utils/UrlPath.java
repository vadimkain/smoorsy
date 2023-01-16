package com.smoorsy.utils;

import lombok.experimental.UtilityClass;

import java.util.Set;

@UtilityClass
public class UrlPath {
    public static final String GET_STATIC_CONTENT = "/static";
    public static final String MAIN_PAGE = "/";
    public static final String REGISTRATION = "/registration";
    public static final String LOGIN = "/login";
    public static final String LOGOUT = "/logout";
    public static final String DEPARTMENTS = "/departments";
    public static final String DEPARTMENTS_INSERT = "/departments/insert/{id}";
    public static final String DEPARTMENTS_EDIT = "/departments/edit/{id}";
    public static final String DEPARTMENTS_DELETE = "/departments/delete/{id}";
}
