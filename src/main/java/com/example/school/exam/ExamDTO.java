package com.example.school.exam;

import java.util.Date;

import com.example.school.classes.Classes;
import com.example.school.Courses.Courses;
import com.example.school.teacher.Teacher;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExamDTO {
    Long id;
    Courses subjects;
    Classes classes;
    Teacher teacher;
    Date date;
}
