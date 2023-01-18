package com.smoorsy.model.service.mapper.user;

import com.smoorsy.model.dto.user.UserDto;
import com.smoorsy.model.entity.users_schema.User;
import com.smoorsy.model.service.mapper.Adapter;

public class UserMapper implements Adapter<User, UserDto> {
    private static final UserMapper INSTANCE = new UserMapper();

    private UserMapper() {
    }

    public static UserMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public UserDto fromObject(User fromObject) {
        return UserDto.builder()
                .id(fromObject.getId())
                .surname(fromObject.getSurname())
                .name(fromObject.getName())
                .patronymic(fromObject.getPatronymic())
                .birthday(fromObject.getBirthday())
                .email(fromObject.getEmail())
                .build();
    }
}
