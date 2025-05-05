package com.example.school.student;

import com.example.school.classes.ClassesDTO;
import com.example.school.courses.Courses;
import com.example.school.courses.CoursesDTO;

import java.util.List;
import java.util.Optional;

public interface IStudentServices {
    
    Boolean isExists(StudentDTO studentDTO);

    void addStudent(StudentDTO studentDTO);
    
    List<StudentDTO> addManyStudent(List<StudentDTO> studentsDTO);

    List<StudentDTO> getAllStudent();

    Optional<StudentDTO> getStudentById(Long id);

    void deleteStudentById(Long id);

    StudentDTO updateStudentById(Long id,StudentDTO studentDTO); 

    Optional<StudentDTO> getByStudentCode(String student_code);

    StudentChartApi getBoysAndGirlsCount();

    List<StudentDTO> getStudentByParentId(Long id);

}
