package com.example.school.lesson;

import com.example.school.classes.Classes;
import com.example.school.courses.Courses;
import com.example.school.teacher.Teacher;

import lombok.Builder;
import lombok.Data;

import java.sql.Date;

@Data
@Builder
public class LessonDTO {
    Long id;
    String course_code;
    String course_name;
    String class_name;
    String teacher_code;
    String teacher_name;
    Date start_date;
}
