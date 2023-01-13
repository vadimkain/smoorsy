package com.smoorsy.model.service.validator;

import com.smoorsy.model.dto.LoginUserDto;

public class LoginUserValidator implements Validator<LoginUserDto> {
    private static final LoginUserValidator INSTANCE = new LoginUserValidator();

    private LoginUserValidator() {
    }

    public static LoginUserValidator getInstance() {
        return INSTANCE;
    }

    @Override
    public ValidationResult isValid(LoginUserDto object) {
        ValidationResult validationResult = new ValidationResult();

        String regexEmail = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

        if (!object.getEmail().toLowerCase().matches(regexEmail) || 120 < object.getEmail().length()) {
            validationResult.add(Error.of("invalid.email", "Неверный формат почты или превышен лимит символов (доступно 120)"));
        }

        if (object.getPassword().length() < 3) {
            validationResult.add(Error.of("invalid.password", "Пароль не может быть меньше трёх символов"));
        }

        return validationResult;
    }
}
