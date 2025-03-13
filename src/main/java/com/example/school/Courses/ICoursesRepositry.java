package com.example.school.Courses;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import jakarta.transaction.Transactional;

public interface ICoursesRepositry extends JpaRepository<Courses,Long>{
    
    @Query("SELECT s FROM Courses s WHERE s.name = ?1")
    Courses getSubjectByName(String subjecName);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM teacher_subjects WHERE teacher_id = :id", nativeQuery = true)
    void deleteTeacher_subject(Long id);

    // @Query("SELECT s FROM Subjects s JOIN Teacher t ON s.subjects.id = t.teacher.id WHERE t.subjects.id = :id")
    @Query("SELECT s FROM Courses s JOIN s.teachers t WHERE t.id = :id")
    List<Courses> getAllSujectsByTeacher_id(Long id);

}
