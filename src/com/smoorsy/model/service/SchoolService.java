package com.smoorsy.model.service;

import com.smoorsy.model.dao.organization_schema.SchoolDao;
import com.smoorsy.model.entity.organization_schema.School;
import com.smoorsy.model.service.validator.ValidationResult;
import com.smoorsy.model.service.validator.exception.ValidationException;
import com.smoorsy.model.service.validator.school.SchoolsOfDepartmentValidator;

import java.util.List;

public class SchoolService {
    private static final SchoolService INSTANCE = new SchoolService();

    private SchoolService() {
    }

    public static SchoolService getInstance() {
        return INSTANCE;
    }

    private final SchoolDao schoolDao = SchoolDao.getInstance();
    private final SchoolsOfDepartmentValidator schoolsOfDepartmentValidator = SchoolsOfDepartmentValidator.getInstance();

    public List<School> findAll() {
        return schoolDao.findAll();
    }

    public List<School> findAllByDepartment(String department_id) {
        // step 1 : validation

        ValidationResult validationResult = schoolsOfDepartmentValidator.isValid(department_id);

        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }

        // step 2 : map
        // step 3 : select
        // step 4 : return
        return schoolDao.findByDepartment(Long.valueOf(department_id));
    }
}
