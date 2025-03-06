package com.example.school.user;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.example.school.user.Permission.ADMIN_CREATE;
import static com.example.school.user.Permission.ADMIN_DELETE;
import static com.example.school.user.Permission.ADMIN_READ;
import static com.example.school.user.Permission.ADMIN_UPDATE;


import static com.example.school.user.Permission.TEACHER_CREATE;
import static com.example.school.user.Permission.TEACHER_READ;
import static com.example.school.user.Permission.TEACHER_UPDATE;
import static com.example.school.user.Permission.TEACHER_DELETE;

import static com.example.school.user.Permission.PARENT_CREATE;
import static com.example.school.user.Permission.PARENT_UPDATE;
import static com.example.school.user.Permission.PARENT_DELETE;
import static com.example.school.user.Permission.PARENT_READ;

import static com.example.school.user.Permission.STUDENT_CREATE;
import static com.example.school.user.Permission.STUDENT_READ;
import static com.example.school.user.Permission.STUDENT_UPDATE;
import static com.example.school.user.Permission.STUDENT_DELETE;

@RequiredArgsConstructor
public enum Role {
    
    ADMIN(
        Set.of(
            ADMIN_CREATE,
            ADMIN_DELETE,
            ADMIN_READ,
            ADMIN_UPDATE
        )
    ),

    STUDENT(
        Set.of(
            STUDENT_CREATE,
            STUDENT_DELETE,
            STUDENT_READ,
            STUDENT_UPDATE
        )
    ),
    TEACHER(
        Set.of(
            TEACHER_READ,
            TEACHER_CREATE,
            TEACHER_UPDATE,
            TEACHER_DELETE
        )
    ),
    PARENT(
        Set.of(
            PARENT_READ,
            PARENT_CREATE,
            PARENT_UPDATE,
            PARENT_DELETE
        )
    );

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities(){
        var authorities = getPermissions()
            .stream()
            .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
            .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return authorities;
    }


}
