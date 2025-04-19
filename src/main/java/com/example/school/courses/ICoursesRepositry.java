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

    @Query("SELECT c FROM Courses c WHERE c.department = :department")
    List<Courses> getAllCoursesByDepartment(String department);

    @Query("SELECT s FROM Courses s JOIN s.teachers t WHERE t.id = :id")
    List<Courses> getAllCoursesByTeacher_id(Long id);

    @Query("SELECT c FROM Courses c JOIN c.teachers t WHERE t.teacher_id = :teacher_code")
    List<Courses> getAllCoursesByTeacherCode(String teacher_code);

    @Query("SELECT c FROM Courses c JOIN c.students s WHERE s.id = :student_id")
    List<Courses> getAllCoursesByStudentId(Long student_id);

    @Query("SELECT c FROM Courses c JOIN c.students s WHERE s.student_id = :student_code")
    List<Courses> getAllCoursesByStudentCode(String student_code);

    @Query("SELECT c FROM Courses c JOIN c.results r WHERE r.id = :result_id")
    List<Courses> getAllCoursesByResultsId(Long result_id);

    @Query("SELECT c FROM Courses c JOIN c.assignments a WHERE a.id = :assignment_id")
    List<Courses> getAllCoursesByAssignmentId(Long assignment_id);

    @Query("SELECT c FROM Courses c JOIN c.classes cl WHERE cl.id = :class_id")
    List<Courses> getAllCoursesByClassesId(Long class_id);

    @Query("SELECT c FROM Courses c JOIN c.classes cl WHERE cl.name = :class_name")
    List<Courses> getAllCoursesByClassesName(String class_name);

    @Query("SELECT c.course_code FROM Courses c WHERE c.course_code LIKE :depart ORDER BY c.course_code DESC LIMIT 1")
    String FindLastCourseCode(String depart);
}
