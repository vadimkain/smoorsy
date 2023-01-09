package com.smoorsy.model.service;

import com.smoorsy.model.dto.RegistrationUserDto;

public class UserService {
    private static final UserService INSTANCE = new UserService();

    private UserService() {
    }

    public static UserService getInstance() {
        return INSTANCE;
    }

    public Long registration(RegistrationUserDto registrationUserDto) {
        // step 1: validation

        // step 2: map
        // step 3: save
        // step 4: return

        return 1L;
    }
}
