package com.smoorsy.model.dto.user;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class LoginUserDto {
    String email;
    String password;
}
