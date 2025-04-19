package com.example.school.classes;

import com.example.school.courses.CoursesDTO;
import com.example.school.result.ResultDTO;
import com.example.school.student.StudentDTO;
import com.example.school.teacher.TeacherDTO;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
public class ClassesDTO {
    Long id;
    String name;
    int capacity;
    int grade;
    String super_visor;
    LocalTime startTime;
    LocalTime endTime;
    LocalDate day;
    String semester;
    String status;
    List<CoursesDTO> courses;
    List<TeacherDTO> teacher;
    List<StudentDTO> student;
    List<ResultDTO> result;
    // Teacher teacher_id;
}
