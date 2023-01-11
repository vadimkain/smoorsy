package com.smoorsy.controller.servlet;

import com.smoorsy.model.service.GetStaticContentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static com.smoorsy.utils.UrlPath.GET_STATIC_CONTENT_FILTER;

@WebServlet(urlPatterns = GET_STATIC_CONTENT_FILTER + "/*")
public class GetStaticContentServlet extends HttpServlet {

    private final GetStaticContentService content = GetStaticContentService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        String requestPath = requestURI.replace("/static", "");

        content.get(requestPath)
                .ifPresentOrElse(
                        (InputStream inputStream) -> {
                            try (OutputStream outputStream = resp.getOutputStream()) {
                                resp.setContentType(req.getHeader("Accept"));
                                outputStream.write(inputStream.readAllBytes());
                            } catch (IOException e) {
                                System.out.println("Произошёл пиздец");
                                throw new RuntimeException(e);
                            }
                        },
                        () -> {
                            System.out.println("Произошел пиздец 2");
                            resp.setStatus(500, "Запрашиваемые ресурсы отсутствуют");
                        }
                );
    }
}
