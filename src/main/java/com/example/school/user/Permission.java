package com.example.school.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {
    
    
    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_DELETE("admin:delete"),
    ADMIN_CREATE("admin:create"),

    STUDENT_READ("student:read"),
    STUDENT_UPDATE("student:update"),
    STUDENT_DELETE("student:delete"),
    STUDENT_CREATE("student:create"),

    TEACHER_READ("teacher:read"),
    TEACHER_UPDATE("teacher:update"),
    TEACHER_DELETE("teacher:delete"),
    TEACHER_CREATE("teacher:create"),

    
    PARENT_READ("parent:read"),
    PARENT_UPDATE("parent:update"),
    PARENT_DELETE("parent:delete"),
    PARENT_CREATE("parent:create");

    @Getter
    private final String permission;
}
