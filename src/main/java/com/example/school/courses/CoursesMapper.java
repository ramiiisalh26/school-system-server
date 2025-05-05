package com.example.school.courses;


import com.example.school.assignment.AssignmentMapper;
import com.example.school.classes.ClassesMapper;
import com.example.school.result.ResultMapper;
import com.example.school.student.StudentMapper;
import com.example.school.teacher.TeacherMapper;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class CoursesMapper {

    public static Courses fromDTOToEntity(CoursesDTO coursesDTO){
        return Courses.builder()
                .id(coursesDTO.getId() == null ? null : coursesDTO.getId())
                .name(coursesDTO.getName())
                .course_code(coursesDTO.getCourse_code())
                .department(coursesDTO.getDepartment())
                .build();
    }

    public static CoursesDTO fromEntityToDTO(Courses courses){
        return CoursesDTO.builder()
                .id(courses.getId() == null ? null : courses.getId())
                .name(courses.getName())
                .course_code(courses.getCourse_code())
                .department(courses.getDepartment())
                .build();
    }
}
