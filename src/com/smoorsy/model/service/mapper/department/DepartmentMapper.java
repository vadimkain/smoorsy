package com.smoorsy.model.service.mapper.department;

import com.smoorsy.model.dto.department.DepartmentDto;
import com.smoorsy.model.dto.department.DepartmentInsertDto;
import com.smoorsy.model.entity.organization_schema.Department;
import com.smoorsy.model.service.mapper.Adapter;

public class DepartmentMapper implements Adapter<DepartmentDto, Department> {

    private static final DepartmentMapper INSTANCE = new DepartmentMapper();

    private DepartmentMapper() {
    }

    public static DepartmentMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public Department fromObject(DepartmentDto fromObject) {
        return Department.builder()
                .id(Long.valueOf(fromObject.getId()))
                .city(fromObject.getCity())
                .build();
    }

    public Department fromObjectWithoutId(DepartmentInsertDto fromObject) {
        return Department.builder()
                .city(fromObject.getCity())
                .build();
    }
}
