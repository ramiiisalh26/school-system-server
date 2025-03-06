package com.example.school.student;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IStudentRepositry extends JpaRepository<Student,Long>{
    
}
