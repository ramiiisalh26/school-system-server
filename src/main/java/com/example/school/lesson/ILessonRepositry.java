package com.example.school.lesson;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ILessonRepositry extends JpaRepository<Lesson,Long>{

    @Query("SELECT l FROM Lesson l JOIN l.courses c WHERE c.course_code = :course_code")
    List<Lesson> getLessonByCourseCode(String course_code);

    @Query("SELECT l FROM Lesson l JOIN l.classes c WHERE c.name = :class_name")
    List<Lesson> getLessonByClassName(String class_name);

    @Query("SELECT l FROM Lesson l JOIN l.teacher t WHERE t.teacher_id = :teacher_code")
    List<Lesson> getLessonByTeacherCode(String teacher_code);

}
