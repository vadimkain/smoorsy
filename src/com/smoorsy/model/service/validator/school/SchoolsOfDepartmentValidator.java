package com.smoorsy.model.service.validator.school;

import com.smoorsy.model.service.validator.Error;
import com.smoorsy.model.service.validator.ValidationResult;
import com.smoorsy.model.service.validator.Validator;

public class SchoolsOfDepartmentValidator implements Validator<String> {
    private static final SchoolsOfDepartmentValidator INSTANCE = new SchoolsOfDepartmentValidator();

    private SchoolsOfDepartmentValidator() {
    }

    public static SchoolsOfDepartmentValidator getInstance() {
        return INSTANCE;
    }

    @Override
    public ValidationResult isValid(String id) {
        ValidationResult validationResult = new ValidationResult();
        try {
            Long department_id = Long.valueOf(id);
        } catch (NumberFormatException numberFormatException) {
            validationResult.add(Error.of("invalidate.department_id", "неверный формат ид департамента или аргумент равен null"));
        }
        return validationResult;
    }
}
