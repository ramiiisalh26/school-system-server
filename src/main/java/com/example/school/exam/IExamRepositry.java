package com.example.school.exam;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IExamRepositry extends JpaRepository<Exam, Long>{

    @Query("SELECT e FROM Exam e JOIN e.courses c WHERE c.course_code = :course_code ")
    List<Exam> getExamByCourseCode(String course_code);

    @Query("SELECT e FROM Exam e JOIN e.classes c WHERE c.name = :class_name")
    List<Exam> getExamByClassName(String class_name);

    @Query("SELECT e FROM Exam e JOIN e.teacher t WHERE t.teacher_id = :teacher_code")
    List<Exam> getExamByTeacherCode(String teacher_code);

}
