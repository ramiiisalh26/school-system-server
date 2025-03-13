package com.example.school.student;

import com.example.school.address.AddressMapper;
import com.example.school.classes.ClassesMapper;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class StudentMapper {
    public static Student fromDTOToEntity(StudentDTO studentDTO){
        return Student.builder()
                .id(studentDTO.getId())
                .first_name(studentDTO.getFirst_name())
                .middle_name(studentDTO.getMiddle_name())
                .last_name(studentDTO.getLast_name())
                .student_id(studentDTO.getStudent_id())
                .phone(studentDTO.getPhone())
                .email(studentDTO.getEmail())
                .grade(studentDTO.getGrade())
                .photo(studentDTO.getPhoto())
                .classes(studentDTO.getClasses() == null ? new ArrayList<>() : studentDTO.getClasses().stream().map(ClassesMapper::fromDTOToEntity).collect(Collectors.toList()))
                .address(AddressMapper.fromDTOToEntity(studentDTO.getAddress()))
                .build();
    }

    public static StudentDTO fromEntityToDTO(Student student){
        return StudentDTO.builder()
                .id(student.getId())
                .first_name(student.getFirst_name())
                .middle_name(student.getMiddle_name())
                .last_name(student.getLast_name())
                .phone(student.getPhone())
                .photo(student.getPhoto())
                .student_id(student.getStudent_id())
                .email(student.getEmail())
                .address(AddressMapper.fromEntityToDTO(student.getAddress()))
                .classes(student.getClasses() == null ? new ArrayList<>() : student.getClasses().stream().map(ClassesMapper::fromEntityToDTO).collect(Collectors.toList()))
                .build();
    }
}
