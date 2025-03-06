package com.example.school.student;

public class StudentMapper {
    public static Student fromDTOToEntity(StudentDTO studentDTO){
        return Student.builder()
            .id(studentDTO.getId())
            .name(studentDTO.getName())
            .student_id(studentDTO.getStudent_id())
            .phone(studentDTO.getPhone())
            .email(studentDTO.getEmail())
            .grade(studentDTO.getGrade())
            .photo(studentDTO.getPhoto())
            .classes(studentDTO.getClasses())
            .address(studentDTO.getAddress())
            .build();
    }

    public static StudentDTO fromEntityToDTO(Student student){
        return StudentDTO.builder()
            .id(student.getId())
            .name(student.getName())
            .phone(student.getPhone())
            .photo(student.getPhoto())
            .student_id(student.getStudent_id())
            .email(student.getEmail())
            .address(student.getAddress())
            .classes(student.getClasses())
            .build();
    }
}
