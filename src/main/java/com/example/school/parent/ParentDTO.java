package com.example.school.parent;

import java.util.List;

import com.example.school.address.Address;
import com.example.school.student.StudentDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ParentDTO {
    Long id;
    String name;
    String email;
    String phone;
    Address address;
    List<StudentDTO> student;
}
