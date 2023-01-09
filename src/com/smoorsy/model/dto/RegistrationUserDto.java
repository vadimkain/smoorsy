package com.smoorsy.model.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

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
