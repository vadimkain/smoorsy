package com.smoorsy.controller.filter;

import com.smoorsy.utils.UrlPath;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@WebFilter("/static/bootstrap/css/bootstrap.css")
public class BootstrapFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try (OutputStream outputStream = servletResponse.getOutputStream()) {
            byte[] body = Files.readAllBytes(Path.of("C:\\Users\\kainv\\Desktop\\Закрепление знаний\\Smoorsy\\resources\\static\\bootstrap\\css\\bootstrap.css"));
            servletResponse.setContentType("text/css");
            outputStream.write(body);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
