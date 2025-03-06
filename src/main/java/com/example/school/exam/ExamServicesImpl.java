package com.example.school.exam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExamServicesImpl implements IExamServices{

    private IExamRepositry examRepositry;

    @Autowired
    public ExamServicesImpl(final IExamRepositry examRepositry){
        this.examRepositry = examRepositry;
    }

    @Override
    public Boolean isExist(ExamDTO examDTO) {
        return examRepositry.existsById(examDTO.getId());
    }

    @Override
    public List<ExamDTO> addManyExams(List<ExamDTO> examsDTO) {
        for (ExamDTO examDTO : examsDTO) {
            addExam(examDTO);
        }
        return examsDTO;
    }

    @Override
    public ExamDTO addExam(ExamDTO examDTO) {
        if (examDTO == null) {
            throw new RuntimeException("Exam Must be provided");
        }

        Exam exam = ExamMapper.fromDTOToEntity(examDTO);

        Exam savedExam = examRepositry.save(exam);

        return ExamMapper.fromEntityToDTO(savedExam);
    }

    @Override
    public Optional<ExamDTO> getExamById(Long id) {
        Optional<Exam> exam = examRepositry.findById(id);
        if (exam.isPresent()) {
            return Optional.of(ExamMapper.fromEntityToDTO(exam.get()));
        }
        return Optional.empty();
    }

    @Override
    public List<ExamDTO> getAllExams() {
        List<Exam> exams = examRepositry.findAll();
        List<ExamDTO> examsDTO = exams.stream().map(exam -> ExamMapper.fromEntityToDTO(exam)).collect(Collectors.toList());
        return examsDTO;
    }

    @Override
    public ExamDTO updateExam(Long id,ExamDTO examDTO) {
        Exam exam = examRepositry.findById(id).orElseThrow();
        if (exam != null) {
            exam.setClasses(examDTO.getClasses());
            exam.setSubjects(examDTO.getSubjects());
            exam.setTeacher(examDTO.getTeacher());
            exam.setDate(examDTO.getDate());
            examRepositry.save(exam);
        }
        return ExamMapper.fromEntityToDTO(exam);
    }

    @Override
    public void deleteExam(Long id) {
        examRepositry.deleteById(id);
    }
    
}
