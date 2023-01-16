package com.smoorsy.model.service.mapper;

import com.smoorsy.model.dto.UserDto;
import com.smoorsy.model.entity.users_schema.User;

public class CheckRolesOfUserMapper implements Adapter<UserDto, User> {
    private static final CheckRolesOfUserMapper INSTANCE = new CheckRolesOfUserMapper();

    private CheckRolesOfUserMapper() {
    }

    public static CheckRolesOfUserMapper getInstance() {
        return INSTANCE;
    }
    @Override
    public User fromObject(UserDto fromObject) {
        return User.builder()
                .id(fromObject.getId())
                .build();
    }
}
