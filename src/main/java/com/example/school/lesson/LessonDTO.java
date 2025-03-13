package com.example.school.lesson;

import com.example.school.classes.Classes;
import com.example.school.Courses.Courses;
import com.example.school.teacher.Teacher;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LessonDTO {
    Long id;
    Courses subject;
    Classes classes;
    Teacher teacher;
}
