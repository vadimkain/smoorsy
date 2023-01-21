package com.smoorsy.model.service;

import com.smoorsy.model.dao.organization_schema.SchoolDao;
import com.smoorsy.model.dto.school.SchoolDto;
import com.smoorsy.model.dto.school.SchoolInsertDto;
import com.smoorsy.model.entity.organization_schema.School;
import com.smoorsy.model.service.mapper.school.SchoolInsertMapper;
import com.smoorsy.model.service.mapper.school.SchoolMapper;
import com.smoorsy.model.service.validator.ValidationResult;
import com.smoorsy.model.service.validator.exception.ValidationException;
import com.smoorsy.model.service.validator.school.SchoolInsertValidator;
import com.smoorsy.model.service.validator.school.SchoolValidator;
import com.smoorsy.model.service.validator.school.SchoolsOfDepartmentValidator;

import java.util.List;
import java.util.Optional;

public class SchoolService {
    private static final SchoolService INSTANCE = new SchoolService();

    private SchoolService() {
    }

    public static SchoolService getInstance() {
        return INSTANCE;
    }

    private final SchoolDao schoolDao = SchoolDao.getInstance();
    private final SchoolsOfDepartmentValidator schoolsOfDepartmentValidator = SchoolsOfDepartmentValidator.getInstance();
    private final SchoolInsertMapper schoolInsertMapper = SchoolInsertMapper.getINSTANCE();
    private final SchoolInsertValidator schoolInsertValidator = SchoolInsertValidator.getInstance();
    private final SchoolValidator schoolValidator = SchoolValidator.getInstance();
    private final SchoolMapper schoolMapper = SchoolMapper.getInstance();

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

    public Optional<School> insert(SchoolInsertDto schoolInsertDto) {
        // step 1 : validation

        ValidationResult validationResult = schoolInsertValidator.isValid(schoolInsertDto);

        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }

        // step 2 : map

        School school = schoolInsertMapper.fromObject(schoolInsertDto);

        // step 3 : insert

        Optional<School> result = schoolDao.insert(school);

        // step 4 : return
        return result;
    }

    public Optional<School> update(SchoolDto schoolDto) {
        // step 1 : validation

        ValidationResult validationResult = schoolValidator.isValid(schoolDto);

        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }

        // step 2 : map

        School school = schoolMapper.fromObject(schoolDto);

        // step 3 : update

        Optional<School> result = schoolDao.update(school);

        // step 4 : return
        return result;
    }

    public boolean delete(SchoolDto schoolDto) {
        // step 1 : validation

        ValidationResult validationResult = schoolValidator.isValid(schoolDto);

        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }

        // step 2: map

        School school = schoolMapper.fromObject(schoolDto);

        // step 3 : delete
        // return
        return schoolDao.delete(school.getId());
    }

}
