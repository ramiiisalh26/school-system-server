package com.example.school.exam;

public class ExamMapper {
    public static Exam fromDTOToEntity(ExamDTO examDTO){
        return Exam.builder()
            .id(examDTO.getId())
            .subjects(examDTO.getSubjects())
            .classes(examDTO.getClasses())
            .date(examDTO.getDate())
            .build();
    }

    public static ExamDTO fromEntityToDTO(Exam exam){
        return ExamDTO.builder()
            .id(exam.getId())
            .subjects(exam.getSubjects())
            .classes(exam.getClasses())
            .date(exam.getDate())
            .build();
    }
}
