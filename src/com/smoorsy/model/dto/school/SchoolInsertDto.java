package com.smoorsy.model.dto.school;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SchoolInsertDto {
    String department_id;
    String name;
    String manager_id;
}
