package com.example.school.teacher;


import com.example.school.classes.Classes;
import com.example.school.courses.Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import jakarta.transaction.Transactional;

import java.util.List;

public interface ITeacherRepositry extends JpaRepository<Teacher,Long>{

    @Modifying
    @Transactional
    @Query("UPDATE Teacher t SET t.address.id = null WHERE t.address.id = :id")
    void setAddressIdToBeNull(Long id);

    @Query("SELECT s FROM Courses s JOIN s.teachers t WHERE t.id = :id")
    List<Courses> getTeacherByCourseId(Long id);

    @Query("SELECT c FROM Classes c JOIN c.teacher t WHERE t.id = :id")
    List<Classes> getTeacherByClassesId(Long id);
}
