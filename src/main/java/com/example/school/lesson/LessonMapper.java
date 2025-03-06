package com.example.school.lesson;

public class LessonMapper {
    public static Lesson fromDTOToEntity(LessonDTO lessonDTO){
        return Lesson.builder()
            .id(lessonDTO.getId())
            .classes(lessonDTO.getClasses())
            .subject(lessonDTO.getSubject())
            .teacher(lessonDTO.getTeacher())
            .build();
    }

    public static LessonDTO fromEntityToDTO(Lesson lesson){
        return LessonDTO.builder()
            .id(lesson.getId())
            .classes(lesson.getClasses())
            .subject(lesson.getSubject())
            .teacher(lesson.getTeacher())
            .build();
    }
}
