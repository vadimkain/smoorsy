package com.smoorsy.model.entity.process_schema;

import com.smoorsy.model.entity.organization_schema.Lesson_Teacher_Class;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Homework {
    private Lesson_Teacher_Class lessonTeacherClass;
    private LocalDate date;
    private LocalDate temp;
    private String file;
}
