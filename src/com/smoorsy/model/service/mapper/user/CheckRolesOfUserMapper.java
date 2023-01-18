package com.smoorsy.model.service.mapper.user;

import com.smoorsy.model.dto.user.UserDto;
import com.smoorsy.model.entity.users_schema.User;
import com.smoorsy.model.service.mapper.Adapter;

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
