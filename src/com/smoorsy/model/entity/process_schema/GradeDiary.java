package com.smoorsy.model.entity.process_schema;

import com.smoorsy.model.entity.organization_schema.Lesson_Teacher_Class;
import com.smoorsy.model.entity.roles_schema.Learner;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GradeDiary {
    private Lesson_Teacher_Class lessonTeacherClass;
    private LocalDate date;
    // TODO: заменить дискретное значение на Enum
    private Short grade;
    private Learner learner;
}
