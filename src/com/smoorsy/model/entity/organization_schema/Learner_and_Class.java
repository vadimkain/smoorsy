package com.smoorsy.model.entity.organization_schema;

import com.smoorsy.model.entity.roles_schema.Learner;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Learner_and_Class {
    private Learner learner;
    private aClass aClass;
}
