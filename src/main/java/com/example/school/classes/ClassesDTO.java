package com.example.school.classes;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClassesDTO {
    Long id;
    String name;
    int capacity;
    int grade;
    String super_visor;
    // Teacher teacher_id;
}
