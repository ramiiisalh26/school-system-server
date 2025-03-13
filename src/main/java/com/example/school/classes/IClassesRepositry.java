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

    // .name, c.capacity, c.grade, c.super_visor
    @Query("SELECT c FROM Classes c JOIN Teacher t WHERE t.id = :teacher_id")
    List<Classes> getClassesByTeacher_id(Long teacher_id);

//    @Modifying
//    @Transactional
//    @Query("UPDATE Classes c SET c.teacher = null WHERE c.teacher = :id")
//    void setTeacherIdToBeNull(Long id);
}
