package com.example.school.student;

import com.example.school.address.Address;

import com.example.school.address.AddressDTO;
import com.example.school.classes.ClassesDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
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
    String phone;
    int grade;
    List<ClassesDTO> classes;
    AddressDTO address;
}
