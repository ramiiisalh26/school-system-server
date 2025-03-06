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

    void deleteLessoyId(Long id);

}
