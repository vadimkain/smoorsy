package com.smoorsy.model.service;

import com.smoorsy.model.service.exception.ServiceException;
import com.smoorsy.utils.PropertiesUtil;

import java.io.IOException;
import java.io.InputStream;
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

    private final String BASE_PATH = PropertiesUtil.get("static.content.url");

    public Optional<InputStream> get(String PATH) {
        Path FULL_PATH = Path.of(BASE_PATH, PATH);

        try {
            if (Files.exists(FULL_PATH)) {
                return Optional.of(Files.newInputStream(FULL_PATH));
            }
            return Optional.empty();
        } catch (IOException e) {
            throw new ServiceException(e);
        }
    }

}
