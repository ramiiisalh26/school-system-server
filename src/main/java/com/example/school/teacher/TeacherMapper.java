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
//            .courses(teacherDTO.getCourses() == null ? new ArrayList<>() : teacherDTO.getCourses().stream().map(CoursesMapper::fromDTOToEntity).collect(Collectors.toList()))
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
            .build();
    }
}
