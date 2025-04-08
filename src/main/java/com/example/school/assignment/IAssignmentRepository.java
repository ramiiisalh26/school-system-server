package com.example.school.assignment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IAssignmentRepository extends JpaRepository<Assignment, Long>{
    @Query("SELECT a FROM Assignment a JOIN a.student WHERE a.student.student_id = :student_code")
    List<Assignment> getAssignmentsByStudentCode(String student_code);

    @Query("SELECT a FROM Assignment a JOIN a.teacher WHERE a.teacher.teacher_id = :teacher_code")
    List<Assignment> getAssignmentsByTeacherCode(String teacher_code);

    @Query("SELECT a FROM Assignment a JOIN a.courses WHERE a.courses.course_code = :courses_code")
    List<Assignment> getAssignmentsByCoursesCode(String courses_code);

}
