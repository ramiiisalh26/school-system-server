package com.example.school.student;

import com.example.school.classes.Classes;
import com.example.school.courses.Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IStudentRepositry extends JpaRepository<Student,Long>{

    @Query("SELECT s.student_id FROM Student s WHERE s.student_id LIKE CONCAT(:year, '%') ORDER BY s.student_id DESC LIMIT 1")
    String FindLastStudentID(String year);

    @Query("SELECT s FROM Student s WHERE s.student_id = :student_code")
    Student getByStudentCode(String student_code);

    @Query("SELECT s FROM Student s JOIN s.parents p WHERE p.id = :id")
    List<Student> getStudentByParentId(Long id);
}
