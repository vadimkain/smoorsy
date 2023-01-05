package com.smoorsy.model.entity.users_schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private Long id;
    private String surname;
    private String name;
    private String patronymic;
    private LocalDate birthday;
    private String email;
    private String password;
}
