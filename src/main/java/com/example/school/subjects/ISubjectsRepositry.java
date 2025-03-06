package com.example.school.subjects;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import jakarta.transaction.Transactional;

public interface ISubjectsRepositry extends JpaRepository<Subjects,Long>{
    
    @Query("SELECT s FROM Subjects s WHERE s.name = ?1")
    Subjects getSubjectByName(String subjecName);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM teacher_subjects WHERE teacher_id = :id", nativeQuery = true)
    void deleteTeacher_subject(Long id);
}
