package com.smoorsy.model.entity.organization_schema;

import com.smoorsy.model.entity.process_schema.Lesson;
import com.smoorsy.model.entity.roles_schema.Teacher;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lesson_Teacher_Class {
    private Long id;
    private Teacher teacher;
    private aClass aClass;
    private Lesson lesson;
}
