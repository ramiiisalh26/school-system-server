package com.example.school.classes;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Repository
public interface IClassesRepositry extends JpaRepository<Classes,Long>{
    
    @Query("SELECT C FROM Classes C WHERE C.name = ?1")
    Classes getClassesByName(String name);

    @Query("SELECT c FROM Classes c JOIN c.teacher t WHERE t.id = :teacher_id")
    List<Classes> getClassesByTeacher_id(Long teacher_id);

    @Query("SELECT c FROM Classes c JOIN c.teacher t WHERE t.teacher_id = :teacher_code")
    List<Classes> getClassesByTeacher_code(String teacher_code);

    @Query("SELECT c FROM Classes c JOIN c.courses co WHERE co.course_code = :course_code")
    List<Classes> getClassesByCourses_code(String course_code);

    @Query("SELECT c FROM Classes c JOIN c.student s WHERE s.id = :student_id")
    List<Classes> getClassesByStudent_id(Long student_id);

    @Query("SELECT c FROM Classes c JOIN c.student s WHERE s.student_id = :student_code")
    List<Classes> getClassesByStudent_code(String student_code);

    @Query("SELECT c FROM Classes c JOIN c.results r WHERE r.id = :result_id")
    List<Classes> getClassesByResults_id(Long result_id);

//    @Modifying
//    @Transactional
//    @Query("UPDATE Classes c SET c.teacher = null WHERE c.teacher = :id")
//    void setTeacherIdToBeNull(Long id);
}
