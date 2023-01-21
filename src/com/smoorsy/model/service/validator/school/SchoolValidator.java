package com.smoorsy.model.service.validator.school;

import com.smoorsy.model.dto.school.SchoolDto;
import com.smoorsy.model.service.validator.Error;
import com.smoorsy.model.service.validator.ValidationResult;
import com.smoorsy.model.service.validator.Validator;

public class SchoolValidator implements Validator<SchoolDto> {
    private static final SchoolValidator INSTANCE = new SchoolValidator();

    private SchoolValidator() {
    }

    public static SchoolValidator getInstance() {
        return INSTANCE;
    }


    @Override
    public ValidationResult isValid(SchoolDto object) {
        ValidationResult validationResult = new ValidationResult();

        try {
            Long.parseLong(object.getId());
        } catch (NumberFormatException e) {
            validationResult.add(Error.of("id", "id id should be a number"));
        }

        try {
            Long.parseLong(object.getDepartment_id());
        } catch (NumberFormatException e) {
            validationResult.add(Error.of("department_id", "Department id should be a number"));
        }

        try {
            Long.parseLong(object.getManager_id());
        } catch (NumberFormatException e) {
            validationResult.add(Error.of("manager_id", "Manager id should be a number"));
        }

        if (object.getName() == null || object.getName().length() == 0) {
            validationResult.add(Error.of("name", "Name should not be empty"));
        }

        return validationResult;
    }
}
