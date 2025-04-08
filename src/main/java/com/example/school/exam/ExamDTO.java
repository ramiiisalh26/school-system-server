package com.example.school.exam;

import java.util.Date;

import com.example.school.classes.Classes;
import com.example.school.courses.Courses;
import com.example.school.teacher.Teacher;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExamDTO {
    Long id;
    String course_name;
    String course_code;
    String class_name;
    String teacher_name;
    String teacher_code;
    Date date;
}
