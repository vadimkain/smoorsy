package com.smoorsy.model.service.validator.school;

import com.smoorsy.model.dto.school.SchoolInsertDto;
import com.smoorsy.model.service.validator.Error;
import com.smoorsy.model.service.validator.ValidationResult;
import com.smoorsy.model.service.validator.Validator;

public class SchoolInsertValidator implements Validator<SchoolInsertDto> {
    private static SchoolInsertValidator instance;

    private SchoolInsertValidator() {
    }

    public static SchoolInsertValidator getInstance() {
        if (instance == null) {
            instance = new SchoolInsertValidator();
        }
        return instance;
    }

    @Override
    public ValidationResult isValid(SchoolInsertDto school) {
        ValidationResult validationResult = new ValidationResult();

        try {
            Long.parseLong(school.getDepartment_id());
        } catch (NumberFormatException e) {
            validationResult.add(Error.of("department_id", "Department id should be a number"));
        }

        try {
            Long.parseLong(school.getManager_id());
        } catch (NumberFormatException e) {
            validationResult.add(Error.of("manager_id", "Manager id should be a number"));
        }

        if (school.getName() == null || school.getName().length() == 0) {
            validationResult.add(Error.of("name", "Name should not be empty"));
        }

        return validationResult;
    }
}
