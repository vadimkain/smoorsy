package com.smoorsy.model.service;

import com.smoorsy.model.dao.organization_schema.DepartmentDao;
import com.smoorsy.model.dto.department.DepartmentDto;
import com.smoorsy.model.dto.department.DepartmentInsertDto;
import com.smoorsy.model.entity.organization_schema.Department;
import com.smoorsy.model.service.mapper.department.DepartmentMapper;
import com.smoorsy.model.service.validator.department.DepartmentValidator;
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
    private final DepartmentMapper departmentMapper = DepartmentMapper.getInstance();


    public List<Department> findAll() {
        return departmentDao.findAll();
    }

    public boolean delete(DepartmentDto departmentDto) {
        // step 1: Validation

        ValidationResult validationResult = departmentValidator.isValid(departmentDto);

        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }

        // step 2: map

        Department department = departmentMapper.fromObject(departmentDto);

        // step 3: delete
        // step 4: return
        return departmentDao.delete(department.getId());
    }

    public Optional<Department> update(DepartmentDto departmentDto) {
        // step 1:

        ValidationResult validationResult = departmentValidator.isValid(departmentDto);

        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }

        // step 2: map

        Department department = departmentMapper.fromObject(departmentDto);

        // step 3: update

        Optional<Department> result = departmentDao.update(department);

        // step 4: return

        return result;
    }

    public Optional<Department> insert(DepartmentInsertDto departmentInsertDto) {
        // step 1: validation

        ValidationResult validationResult = departmentValidator.isValidWithoutId(departmentInsertDto);

        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }

        // step 2: map

        Department department = departmentMapper.fromObjectWithoutId(departmentInsertDto);

        // step 3: insert

        Optional<Department> result = departmentDao.insert(department);

        // step 4: return

        return result;
    }
}
