package com.smoorsy.model.dto.user;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RegistrationUserDto {
    String surname;
    String name;
    String patronymic;
    String birthday;
    String email;
    String password;
}
