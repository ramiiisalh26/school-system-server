package com.example.school.courses;

import java.util.List;

import com.example.school.assignment.AssignmentDTO;
import com.example.school.classes.ClassesDTO;
import com.example.school.result.ResultDTO;
import com.example.school.student.StudentDTO;
import com.example.school.teacher.TeacherDTO;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
public class CoursesDTO {
    Long id;
    String name;
    String course_code;
    String department;
}
