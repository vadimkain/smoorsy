package com.smoorsy.model.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

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
