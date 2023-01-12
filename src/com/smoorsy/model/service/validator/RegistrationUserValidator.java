package com.smoorsy.model.service.validator;

import com.smoorsy.model.dto.RegistrationUserDto;
import com.smoorsy.model.service.mapper.RegistrationUserMapper;
import com.smoorsy.utils.LocalDateFormatter;

import java.time.LocalDate;

public class RegistrationUserValidator implements Validator<RegistrationUserDto> {
    private static final RegistrationUserValidator INSTANCE = new RegistrationUserValidator();

    private RegistrationUserValidator() {
    }

    public static final RegistrationUserValidator getInstance() {
        return INSTANCE;
    }


    @Override
    public ValidationResult isValid(RegistrationUserDto object) {

        ValidationResult validationResult = new ValidationResult();

        String regexFullname = "^([A-Z]{1}[a-z]{1,63}|[А-Я]{1}[а-я]{1,63})$";
        String regexEmail = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

        if (!object.getSurname().matches(regexFullname)) {
            validationResult.add(Error.of("invalid.surname", "Неверный формат фамилии"));
        }

        if (!object.getName().matches(regexFullname)) {
            validationResult.add(Error.of("invalid.name", "Неверный формат имени"));
        }

        if (!object.getPatronymic().matches(regexFullname)) {
            validationResult.add(Error.of("invalid.patronymic", "Неверный формат отчества"));
        }

        if (!LocalDateFormatter.isValid(object.getBirthday())) {
            validationResult.add(Error.of("invalid.birthday", "Неверный формат даты"));
        }

        LocalDate birthday = RegistrationUserMapper.getInstance().fromObject(object).getBirthday();
        boolean birthdayIsAfter = birthday.isAfter(LocalDate.of(LocalDate.now().getYear() - 6, 1, 1));
        boolean birthdayIsBefore = birthday.isBefore(LocalDate.of(LocalDate.now().getYear() - 100, 1, 1));

        if (LocalDateFormatter.isValid(object.getBirthday()) && (birthdayIsBefore || birthdayIsAfter)) {
            validationResult.add(Error.of("invalid.birthday", "Указан нереальный возраст (можно только от 6 до 100 лет)"));
        }

        if (!object.getEmail().toLowerCase().matches(regexEmail) || 120 < object.getEmail().length()) {
            validationResult.add(Error.of("invalid.email", "Неверный формат почты"));
        }

        return validationResult;
    }
}
