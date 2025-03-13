package com.example.school.student;

import com.example.school.classes.Classes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IStudentRepositry extends JpaRepository<Student,Long>{

    @Query("SELECT c FROM Classes c JOIN c.student s WHERE s.id = :id")
    List<Classes> getStudentClasses(Long id);
//    "SELECT s.student_id FROM Student s WHERE s.student_id LIKE :year% ORDER BY s.student_id DESC LIMIT 1"
    @Query("SELECT s.student_id FROM Student s WHERE s.student_id LIKE CONCAT(:year, '%') ORDER BY s.student_id DESC LIMIT 1")
    String FindLastStudentID(String year);

    @Query("SELECT s FROM Student s WHERE s.student_id = :student_id")
    Student FindByStudentID(String student_id);
}
