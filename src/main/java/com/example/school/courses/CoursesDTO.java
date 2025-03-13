package com.example.school.Courses;

import java.util.List;

import com.example.school.teacher.TeacherDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CoursesDTO {
    Long id;
    String name;
    List<TeacherDTO> teachers;
}
