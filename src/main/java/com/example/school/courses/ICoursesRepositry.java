package com.example.school.courses;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import jakarta.transaction.Transactional;

public interface ICoursesRepositry extends JpaRepository<Courses,Long>{
    
    @Query("SELECT s FROM Courses s WHERE s.name = ?1")
    Courses getCourseByName(String courseName);

    @Query("SELECT c FROM Courses c WHERE c.course_code = :course_code")
    Courses getCourseByCourse_code(String course_code);

    @Query("SELECT s FROM Courses s JOIN s.teachers t WHERE t.id = :id")
    List<Courses> getAllCoursesByTeacher_id(Long id);

    @Query("SELECT c.course_code FROM Courses c WHERE c.course_code LIKE :depart ORDER BY c.course_code DESC LIMIT 1")
    String FindLastCourseCode(String depart);
}
