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
                .assignments(courses.getAssignments() == null ? new ArrayList<>() : courses.getAssignments().stream().map(AssignmentMapper::fromEntityToDTO).collect(Collectors.toList()))
                .classes(courses.getClasses() == null ? new ArrayList<>() : courses.getClasses().stream().map(ClassesMapper::fromEntityToDTO).collect(Collectors.toList()))
                .results(courses.getResults() == null ? new ArrayList<>() : courses.getResults().stream().map(ResultMapper::fromEntityToDTO).collect(Collectors.toList()))
                .students(courses.getStudents() == null ? new ArrayList<>() : courses.getStudents().stream().map(StudentMapper::fromEntityToDTO).collect(Collectors.toList()))
                .teachers(courses.getTeachers() == null ? new ArrayList<>() : courses.getTeachers().stream().map(TeacherMapper::fromEntityToDTO).collect(Collectors.toList()))
                .build();
    }
}
