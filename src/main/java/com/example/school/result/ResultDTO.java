package com.example.school.result;

import java.util.Date;

import com.example.school.student.StudentDTO;
import com.example.school.courses.CoursesDTO;
import com.example.school.teacher.TeacherDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResultDTO {
    Long id;
    CoursesDTO coursesDTO;
    StudentDTO studentDTO;
    TeacherDTO teacherDTO;
    Date date;
    String type;
    int score;
}
