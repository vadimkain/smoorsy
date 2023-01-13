package com.smoorsy.model.service.mapper;

import com.smoorsy.model.dto.LoginUserDto;
import com.smoorsy.model.entity.users_schema.User;

public class LoginUserMapper implements Adapter<LoginUserDto, User> {

    private static final LoginUserMapper INSTANCE = new LoginUserMapper();

    private LoginUserMapper() {
    }

    public static LoginUserMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public User fromObject(LoginUserDto fromObject) {
        return User.builder()
                .email(fromObject.getEmail())
                .password(fromObject.getPassword())
                .build();
    }
}
