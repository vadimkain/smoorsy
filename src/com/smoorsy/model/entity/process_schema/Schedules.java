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
public class Schedules {
    private Lesson_Teacher_Class lessonTeacherClass;
    private LocalDate day_of_week;
}
