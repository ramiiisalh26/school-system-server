package com.example.school.lesson;

public class LessonMapper {
//    public static Lesson fromDTOToEntity(LessonDTO lessonDTO){
//        return Lesson.builder()
//            .id(lessonDTO.getId())
//            .classes(lessonDTO.getClasses())
//            .courses(lessonDTO.getCourses())
//            .teacher(lessonDTO.getTeacher())
//            .build();
//    }

    public static LessonDTO fromEntityToDTO(Lesson lesson){
        return LessonDTO.builder()
                .id(lesson.getId())
                .class_name(lesson.getClasses().getName())
                .course_name(lesson.getCourses().getName())
                .course_code(lesson.getCourses().getCourse_code())
                .teacher_code(lesson.getTeacher().getTeacher_id())
                .teacher_name(lesson.getTeacher().getFirst_name().concat(" " + lesson.getTeacher().getLast_name()))
                .build();
    }
}
