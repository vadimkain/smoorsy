package com.smoorsy.model.entity.users_schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Users_and_Roles {
    private User user;
    private Role role;
}
