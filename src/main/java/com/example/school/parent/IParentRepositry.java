package com.example.school.parent;

import com.example.school.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IParentRepositry extends JpaRepository<Parent,Long>{

    @Query("SELECT s FROM Student s JOIN s.parents p WHERE p.id = :id")
    List<Student> getStudentByParentId(Long id);
}
