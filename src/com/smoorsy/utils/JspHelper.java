package com.smoorsy.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class JspHelper {
    public static String getPath(String jspName) {
        return String.format("WEB-INF/jsp/%s.jsp", jspName);
    }
}
