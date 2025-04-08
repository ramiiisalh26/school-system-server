package com.example.school.assignment;

import java.util.Date;

import com.example.school.classes.Classes;
import com.example.school.courses.Courses;
import com.example.school.teacher.Teacher;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AssignmentDTO {
    Long id;
    String course_code;
    String student_code;
    String class_name;
    String teacher_code;
    Date dueDate;
}
