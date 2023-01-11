package com.smoorsy.controller.filter;

import com.smoorsy.model.service.GetStaticContentService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;

import static com.smoorsy.utils.UrlPath.GET_STATIC_CONTENT_FILTER;
import static jakarta.servlet.DispatcherType.REQUEST;

@WebFilter(urlPatterns = GET_STATIC_CONTENT_FILTER + "/*", dispatcherTypes = REQUEST)
public class GetStaticContentFilter implements Filter {
    private final GetStaticContentService content = GetStaticContentService.getInstance();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String requestURI = ((HttpServletRequest) servletRequest).getRequestURI();
        String requestPath = requestURI.replace("/static", "");

        content.get(requestPath)
                .ifPresentOrElse(
                        (InputStream inputStream) -> {
                            try {
                                servletResponse.setContentType("text/css");
                                servletResponse.getOutputStream().write(inputStream.readAllBytes());
                            } catch (IOException e) {
                                System.out.println("Произошёл пиздец");
                                throw new RuntimeException(e);
                            }
                        },
                        () -> {
                            System.out.println("Произошел пиздец 2");
                            ((HttpServletResponse) servletResponse).setStatus(500, "Статические ресурсы не загрузились");
                        }
                );

        filterChain.doFilter(servletRequest, servletResponse);
    }
}