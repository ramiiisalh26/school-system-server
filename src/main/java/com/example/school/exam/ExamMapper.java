package com.example.school.exam;

public class ExamMapper {
//    public static Exam fromDTOToEntity(ExamDTO examDTO){
//        return Exam.builder()
//            .id(examDTO.getId())
//            .courses(examDTO.getCourses())
//            .classes(examDTO.getClasses())
//            .date(examDTO.getDate())
//            .build();
//    }

    public static ExamDTO fromEntityToDTO(Exam exam){
        return ExamDTO.builder()
                .id(exam.getId())
                .course_code(exam.getCourses().getCourse_code())
                .course_name(exam.getCourses().getName())
                .class_name(exam.getClasses().getName())
                .teacher_code(exam.getTeacher().getTeacher_id())
                .teacher_name(exam.getTeacher().getFirst_name().concat(" " + exam.getTeacher().getLast_name()))
                .date(exam.getDate())
                .build();
    }
}
