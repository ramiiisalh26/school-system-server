package com.example.school.teacher;

import java.util.List;

import com.example.school.address.AddressDTO;
import com.example.school.classes.ClassesDTO;
import com.example.school.courses.CoursesDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TeacherDTO {
    Long id;
    String teacher_id;
    String first_name;
    String middle_name;
    String last_name;
    String email;
    String photo;
    String phone;
    List<CoursesDTO> courses;
    List<ClassesDTO> classes;
    AddressDTO address;
}
