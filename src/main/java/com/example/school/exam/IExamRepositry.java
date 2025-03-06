package com.example.school.exam;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IExamRepositry extends JpaRepository<Exam, Long>{
    
}
