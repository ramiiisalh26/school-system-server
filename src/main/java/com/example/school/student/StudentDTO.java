package com.example.school.student;

import com.example.school.address.Address;

import com.example.school.address.AddressDTO;
import com.example.school.assignment.AssignmentDTO;
import com.example.school.classes.ClassesDTO;
import com.example.school.courses.CoursesDTO;
import com.example.school.result.ResultDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
public class StudentDTO {
    Long id;
    Integer version;
    String student_id;
    String first_name;
    String middle_name;
    String last_name;
    String email;
    String photo;
    Gender gender;
    String phone;
    int grade;
    AddressDTO address;
}
