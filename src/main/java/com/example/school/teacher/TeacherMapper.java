package com.example.school.teacher;

import java.util.ArrayList;
import java.util.stream.Collectors;

import com.example.school.address.AddressMapper;
import com.example.school.classes.ClassesMapper;
import com.example.school.courses.CoursesMapper;

public class TeacherMapper {
    public static Teacher fromDTOToEntity(TeacherDTO teacherDTO){
        return Teacher.builder()
            .id(teacherDTO.getId())
            .first_name(teacherDTO.getFirst_name())
            .middle_name(teacherDTO.getMiddle_name())
            .last_name(teacherDTO.getLast_name())
            .email(teacherDTO.getEmail())
            .phone(teacherDTO.getPhone())
            .photo(teacherDTO.getPhoto())
            .address(AddressMapper.fromDTOToEntity(teacherDTO.getAddress()))
            .teacher_id(teacherDTO.getTeacher_id())
            .courses(teacherDTO.getCoursesDTO() == null ? new ArrayList<>() : teacherDTO.getCoursesDTO().stream().map(CoursesMapper::fromDTOToEntity).collect(Collectors.toList()))
            .classes(teacherDTO.getClasses()== null ? new ArrayList<>() : teacherDTO.getClasses().stream().map(ClassesMapper::fromDTOToEntity).collect(Collectors.toList()))
            .build();
    }

    public static TeacherDTO fromEntityToDTO(Teacher teacher){
        return TeacherDTO.builder()
            .id(teacher.getId())
            .first_name(teacher.getFirst_name())
            .middle_name(teacher.getMiddle_name())
            .last_name(teacher.getLast_name())
            .email(teacher.getEmail())
            .phone(teacher.getPhone())
            .photo(teacher.getPhoto())
            .address(AddressMapper.fromEntityToDTO(teacher.getAddress()))
            .teacher_id(teacher.getTeacher_id())
            .coursesDTO(teacher.getCourses() == null
            ? new ArrayList<>() 
            : teacher.getCourses().stream()
            .map(CoursesMapper::fromEntityToDTO).collect(Collectors.toList()))
            .classes(teacher.getClasses() == null 
            ? new ArrayList<>() 
            : teacher.getClasses().stream().map(ClassesMapper::fromEntityToDTO).collect(Collectors.toList()))
            .build();
    }
}
