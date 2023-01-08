package com.smoorsy.model.entity.process_schema;

import com.smoorsy.model.entity.organization_schema.Lesson_Teacher_Class;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Schedules {
    private Lesson_Teacher_Class lessonTeacherClass;
    private LocalDate day_of_week;
    private LocalTime time_begin;
    private LocalTime time_end;
    private LocalTime rest_time;
}
