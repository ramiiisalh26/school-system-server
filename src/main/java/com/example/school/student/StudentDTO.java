package com.example.school.student;

import com.example.school.address.Address;
import com.example.school.classes.Classes;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentDTO {
    Long id;
    String student_id;
    String name;
    String email;
    String photo;
    String phone;
    int grade;
    Classes classes;
    Address address;

}
