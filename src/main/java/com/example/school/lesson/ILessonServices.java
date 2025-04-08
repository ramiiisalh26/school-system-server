package com.example.school.lesson;

import java.util.List;
import java.util.Optional;

public interface ILessonServices {
    
    Boolean isExist(LessonDTO lessonDTO);

    List<LessonDTO> addManyLessons(List<LessonDTO> lessonsDTO);

    LessonDTO addLesson(LessonDTO lessonDTO);

    Optional<LessonDTO> getLessonById(Long id);

    List<LessonDTO> getAllLessons();

    LessonDTO updateLessons(Long id,LessonDTO lessonDTO);

    void deleteLessonById(Long id);

    List<LessonDTO> getLessonByCourseCode(String course_code);

    List<LessonDTO> getLessonByClassName(String class_name);

    List<LessonDTO> getLessonByTeacherCode(String teacher_code);
}