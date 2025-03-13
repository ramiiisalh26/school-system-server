package com.example.school.parent;

import java.util.ArrayList;
import java.util.List;

import com.example.school.address.Address;
import com.example.school.address.AddressDTO;
import com.example.school.student.StudentDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ParentDTO {
    Long id;
    String first_name;
    String middle_name;
    String last_name;
    String email;
    String phone;
    AddressDTO address;
    List<StudentDTO> students;
}
