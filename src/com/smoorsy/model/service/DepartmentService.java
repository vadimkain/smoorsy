package com.smoorsy.model.service;

import com.smoorsy.model.dao.organization_schema.DepartmentDao;
import com.smoorsy.model.entity.organization_schema.Department;
import com.smoorsy.model.service.validator.DepartmentValidator;
import com.smoorsy.model.service.validator.ValidationResult;
import com.smoorsy.model.service.validator.exception.ValidationException;

import java.util.List;
import java.util.Optional;

public class DepartmentService {
    private static final DepartmentService INSTANCE = new DepartmentService();

    private DepartmentService() {
    }

    public static DepartmentService getInstance() {
        return INSTANCE;
    }

    private final DepartmentDao departmentDao = DepartmentDao.getInstance();
    private final DepartmentValidator departmentValidator = DepartmentValidator.getInstance();


    public List<Department> findAll() {
        return departmentDao.findAll();
    }

    public boolean delete(Long key) {
        return departmentDao.delete(key);
    }

    public Optional<Department> update(Department entity) {
        // step 1:

        ValidationResult validationResult = departmentValidator.isValid(entity);

        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }

        // step 2: map

        // step 3: update

        Optional<Department> department = departmentDao.update(entity);

        // step 4: return

        return department;
    }

    public Optional<Department> insert(Department entity) {
        // step 1: validation

        ValidationResult validationResult = departmentValidator.isValid(entity);

        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }

        // step 2: map

        // step 3: insert

        Optional<Department> department = departmentDao.insert(entity);

        // step 4: return

        return department;
    }
}
