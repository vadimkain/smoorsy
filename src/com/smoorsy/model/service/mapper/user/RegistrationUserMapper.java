package com.smoorsy.model.service.mapper.user;

import com.smoorsy.model.dto.user.RegistrationUserDto;
import com.smoorsy.model.entity.users_schema.User;
import com.smoorsy.model.service.mapper.Adapter;
import com.smoorsy.utils.LocalDateFormatter;

public class RegistrationUserMapper implements Adapter<RegistrationUserDto, User> {

    private static final RegistrationUserMapper INSTANCE = new RegistrationUserMapper();

    private RegistrationUserMapper() {
    }

    public static RegistrationUserMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public User fromObject(RegistrationUserDto fromObject) {
        return User.builder()
                .surname(fromObject.getSurname())
                .name(fromObject.getName())
                .patronymic(fromObject.getPatronymic())
                .birthday(LocalDateFormatter.format(fromObject.getBirthday()))
                .email(fromObject.getEmail())
                .password(fromObject.getPassword())
                .build();
    }
}
