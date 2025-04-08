package com.example.school.courses;

import java.util.List;

import com.example.school.teacher.TeacherDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CoursesDTO {
    Long id;
    String name;
    String course_code;
    String department;
    List<TeacherDTO> teachers;
}
