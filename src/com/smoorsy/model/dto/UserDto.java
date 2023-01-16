package com.smoorsy.model.dto;

import com.smoorsy.model.entity.users_schema.Role;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.util.List;

@Value
@Builder
public class UserDto {
    Long id;
    String surname;
    String name;
    String patronymic;
    LocalDate birthday;
    String email;
}
