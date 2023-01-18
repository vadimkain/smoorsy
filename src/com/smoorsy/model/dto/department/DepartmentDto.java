package com.smoorsy.model.dto.department;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DepartmentDto {
    String id;
    String city;
}
