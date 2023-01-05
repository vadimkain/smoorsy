package com.smoorsy.model.entity.organization_schema;

import com.smoorsy.model.entity.roles_schema.ClassroomTeacher;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class aClass {
    private Long id;
    private School school;
    private ClassroomTeacher classroomTeacher;
    private String title;
}
