package com.smoorsy.model.service.validator;

import com.smoorsy.model.dto.RegistrationUserDto;

public class RegistrationUserValidator implements Validator<RegistrationUserDto> {
    private static final RegistrationUserValidator INSTANCE = new RegistrationUserValidator();

    private RegistrationUserValidator() {
    }

    public static final RegistrationUserValidator getInstance() {
        return INSTANCE;
    }


    @Override
    public ValidationResult isValid(RegistrationUserDto object) {
//        surname; ^[A-z]{1}[a-z]{1, 63}$ matches()
//        name;
//        patronymic;
//        birthday;
//        email;
//        password;
        return null;
    }
}
