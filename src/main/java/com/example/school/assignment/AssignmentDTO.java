package com.example.school.assignment;

import java.util.Date;

import com.example.school.classes.Classes;
import com.example.school.subjects.Subjects;
import com.example.school.teacher.Teacher;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AssignmentDTO {
    Long id;
    Subjects subjects;
    Classes classes;
    Teacher teacher;
    Date dueDate;
}
