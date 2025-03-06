package com.example.school.result;

import java.util.Date;

import com.example.school.classes.Classes;
import com.example.school.subjects.Subjects;
import com.example.school.teacher.Teacher;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResultDTO {
    Long id;
    Subjects subjects;
    Classes classes;
    Teacher teacher;
    Date date;
    String type;
    int score;
}
