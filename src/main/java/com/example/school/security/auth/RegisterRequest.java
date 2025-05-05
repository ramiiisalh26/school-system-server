package com.example.school.security.auth;

import com.example.school.user.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    private String first_name;
    private String last_name;
    private String username;
    private String password;
    private String re_password;
    private Role role;
}
