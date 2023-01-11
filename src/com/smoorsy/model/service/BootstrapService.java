package com.smoorsy.model.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class BootstrapService {
    private static final BootstrapService INSTANCE = new BootstrapService();

    private BootstrapService() {
    }

    public static BootstrapService getInstance() {
        return INSTANCE;
    }

    public Optional<InputStream> getCss(String fullpath) {
        Path cssPath = Path.of(fullpath);
        try {
            if (Files.exists(cssPath)) {
                return Optional.of(Files.newInputStream(cssPath));
            }
            return Optional.empty();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
