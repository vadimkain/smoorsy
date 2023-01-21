package com.smoorsy.model.service.mapper.school;

import com.smoorsy.model.dao.organization_schema.DepartmentDao;
import com.smoorsy.model.dao.roles_schema.ManagerDao;
import com.smoorsy.model.dto.school.SchoolDto;
import com.smoorsy.model.entity.organization_schema.School;
import com.smoorsy.model.service.mapper.Adapter;

public class SchoolMapper implements Adapter<SchoolDto, School> {
    private static final SchoolMapper INSTANCE = new SchoolMapper();

    private SchoolMapper() {
    }

    public static SchoolMapper getInstance() {
        return INSTANCE;
    }

    private final DepartmentDao departmentDao = DepartmentDao.getInstance();
    private final ManagerDao managerDao = ManagerDao.getInstance();

    @Override
    public School fromObject(SchoolDto fromObject) {
        return School.builder()
                .id(Long.valueOf(fromObject.getId()))
                .department(departmentDao.findById(Long.valueOf(fromObject.getDepartment_id())).get())
                .name(fromObject.getName())
                .manager(managerDao.findById(Long.valueOf(fromObject.getManager_id())).get())
                .build();
    }
}
