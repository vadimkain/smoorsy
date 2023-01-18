package com.smoorsy.model.service.validator.department;

import com.smoorsy.model.dto.department.DepartmentDto;
import com.smoorsy.model.dto.department.DepartmentInsertDto;
import com.smoorsy.model.service.validator.Error;
import com.smoorsy.model.service.validator.ValidationResult;
import com.smoorsy.model.service.validator.Validator;

public class DepartmentValidator implements Validator<DepartmentDto> {
    private static final DepartmentValidator INSTANCE = new DepartmentValidator();

    private DepartmentValidator() {
    }

    public static DepartmentValidator getInstance() {
        return INSTANCE;
    }

    @Override
    public ValidationResult isValid(DepartmentDto object) {
        ValidationResult validationResult = new ValidationResult();

        String regexId = "^[0-9]$";
        String regexCity = "^([A-Z]{1}[a-z]{1,63}|[А-Я]{1}[а-я]{1,63})$";

        if (!object.getId().matches(regexId)) {
            validationResult.add(Error.of("invalid.department.id", "Неверный формат ID"));
        }

        if (!object.getCity().matches(regexCity)) {
            validationResult.add(Error.of("invalid.department.city", "Неверный формат названия города"));
        }

        return validationResult;
    }

    public ValidationResult isValidWithoutId(DepartmentInsertDto object) {
        ValidationResult validationResult = new ValidationResult();

        String regexCity = "^([A-Z]{1}[a-z]{1,63}|[А-Я]{1}[а-я]{1,63})$";


        if (!object.getCity().matches(regexCity)) {
            validationResult.add(Error.of("invalid.department.city", "Неверный формат названия города"));
        }

        return validationResult;
    }
}
