package com.example.school.exam;

import java.util.List;
import java.util.Optional;

public interface IExamServices {
    
    Boolean isExist(ExamDTO examDTO);

    List<ExamDTO> addManyExams(List<ExamDTO> examsDTO);

    ExamDTO addExam(ExamDTO examDTO);

    Optional<ExamDTO> getExamById(Long id);

    List<ExamDTO> getAllExams();

    ExamDTO updateExam(Long id,ExamDTO examDTO);

    void deleteExam(Long id);

    List<ExamDTO> getExamsByClassName(String class_name);

    List<ExamDTO> getExamsByTeacherCode(String teacher_code);

    List<ExamDTO> getExamsByCourseCode(String course_code);

    List<ExamDTO> getExamByCourseName(String course_name);
}
