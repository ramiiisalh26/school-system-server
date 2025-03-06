package com.example.school.lesson;

import com.example.school.classes.Classes;
import com.example.school.subjects.Subjects;
import com.example.school.teacher.Teacher;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LessonDTO {
    Long id;
    Subjects subject;
    Classes classes;
    Teacher teacher;
}
