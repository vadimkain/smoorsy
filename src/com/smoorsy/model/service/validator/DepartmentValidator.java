package com.smoorsy.model.service.validator;

import com.smoorsy.model.entity.organization_schema.Department;

public class DepartmentValidator implements Validator<Department> {
    private static final DepartmentValidator INSTANCE = new DepartmentValidator();

    private DepartmentValidator() {
    }

    public static DepartmentValidator getInstance() {
        return INSTANCE;
    }

    @Override
    public ValidationResult isValid(Department object) {
        ValidationResult validationResult = new ValidationResult();

        String regexCity = "^([A-Z]{1}[a-z]{1,63}|[А-Я]{1}[а-я]{1,63})$";

        if (!object.getCity().matches(regexCity)) {
            validationResult.add(Error.of("invalid.department.city", "Неверный формат названия города"));
        }

        return validationResult;
    }
}
