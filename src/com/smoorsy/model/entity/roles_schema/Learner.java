package com.smoorsy.model.entity.roles_schema;

import com.smoorsy.model.entity.users_schema.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Learner {
    private User user;
}
