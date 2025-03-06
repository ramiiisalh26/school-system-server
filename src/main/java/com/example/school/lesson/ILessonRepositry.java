package com.example.school.lesson;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ILessonRepositry extends JpaRepository<Lesson,Long>{
    
}
