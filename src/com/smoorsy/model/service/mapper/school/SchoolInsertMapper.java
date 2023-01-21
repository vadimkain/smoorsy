package com.smoorsy.model.service.mapper.school;

import com.smoorsy.model.dao.organization_schema.DepartmentDao;
import com.smoorsy.model.dao.roles_schema.ManagerDao;
import com.smoorsy.model.dto.school.SchoolInsertDto;
import com.smoorsy.model.entity.organization_schema.School;
import com.smoorsy.model.service.mapper.Adapter;

public class SchoolInsertMapper implements Adapter<SchoolInsertDto, School> {
    private static final SchoolInsertMapper INSTANCE = new SchoolInsertMapper();

    private SchoolInsertMapper() {
    }

    public static SchoolInsertMapper getINSTANCE() {
        return INSTANCE;
    }

    private final DepartmentDao departmentDao = DepartmentDao.getInstance();
    private final ManagerDao managerDao = ManagerDao.getInstance();

    @Override
    public School fromObject(SchoolInsertDto fromObject) {
        return School.builder()
                .department(departmentDao.findById(Long.valueOf(fromObject.getDepartment_id())).get())
                .name(fromObject.getName())
                .manager(managerDao.findById(Long.valueOf(fromObject.getManager_id())).get())
                .build();
    }
}