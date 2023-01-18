package com.smoorsy.model.service;

import com.smoorsy.model.service.exception.ServiceException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class GetStaticContentService {
    private static final GetStaticContentService INSTANCE = new GetStaticContentService();

    private GetStaticContentService() {
    }

    public static GetStaticContentService getInstance() {
        return INSTANCE;
    }

    //    private final String BASE_PATH = PropertiesUtil.get("static.content.url");
    private final String BASE_PATH = "C:\\Users\\kainv\\Desktop\\Закрепление знаний\\Smoorsy\\resources\\static";

    public Optional<InputStream> get(String PATH) {
        Path FULL_PATH = Path.of(BASE_PATH, PATH);

        try {
            if (Files.exists(FULL_PATH)) {
                return Optional.of(Files.newInputStream(FULL_PATH));
            }
            return Optional.empty();
        } catch (AccessDeniedException e) {
            throw new ServiceException("The full path exists, but no resources were found", e);
        } catch (IOException e) {
            throw new ServiceException("Resources not found", e);
        }
    }
}
