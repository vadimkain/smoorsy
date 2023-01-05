package com.smoorsy.model.entity.organization_schema;

import com.smoorsy.model.entity.roles_schema.Manager;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class School {
    private Long id;
    private Department department;
    private String name;
    private Manager manager;
}
