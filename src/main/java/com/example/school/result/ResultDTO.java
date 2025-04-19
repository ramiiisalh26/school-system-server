package com.example.school.result;

import java.util.Date;

import com.example.school.classes.ClassesDTO;
import com.example.school.student.StudentDTO;
import com.example.school.courses.CoursesDTO;
import com.example.school.teacher.TeacherDTO;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResultDTO {
    Long id;
    String course_code;
    String student_code;
    String teacher_id;
    String class_name;
    Date date;
    String type;
    int score;
}
