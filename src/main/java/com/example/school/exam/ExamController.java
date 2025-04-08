package com.example.school.exam;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/exam")
public class ExamController {
    
    private final IExamServices IexamServices;
    
    @Autowired
    public ExamController(final IExamServices IexamServices){
        this.IexamServices = IexamServices;
    }

    @PostMapping(path = "/add")
    public ResponseEntity<List<ExamDTO>> addExams(@RequestBody final List<ExamDTO> examsDTO){
        final List<ExamDTO> savedExams = IexamServices.addManyExams(examsDTO);
        return new ResponseEntity<>(savedExams,HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ExamDTO> getExamById(@PathVariable final Long id){
        final Optional<ExamDTO> examDTO = IexamServices.getExamById(id);
        return examDTO.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<ExamDTO>> getAllExams(){
        return new ResponseEntity<>(IexamServices.getAllExams(),HttpStatus.OK);
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<ExamDTO> updateExam(@PathVariable final Long id, @RequestBody final ExamDTO examDTO){
        final boolean exam = IexamServices.isExist(examDTO);
        if (exam) {
            final ExamDTO updatedExam = IexamServices.updateExam(id, examDTO);
            return new ResponseEntity<>(updatedExam,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<ExamDTO> deleteExam(@PathVariable final Long id){
        IexamServices.deleteExam(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @GetMapping(path = "getExamsByClassName/{class_name}")
    public ResponseEntity<List<ExamDTO>> getExamsByClassName(@PathVariable final String class_name){
        return new ResponseEntity<>(IexamServices.getExamsByClassName(class_name),HttpStatus.OK);
    }

    @GetMapping(path = "getExamsByTeacherCode/{teacher_code}")
    public ResponseEntity<List<ExamDTO>> getExamsByTeacherCode(@PathVariable final String teacher_code){
        return new ResponseEntity<>(IexamServices.getExamsByTeacherCode(teacher_code),HttpStatus.OK);
    }

    @GetMapping(path = "getExamsByCourseCode/{course_code}")
    public ResponseEntity<List<ExamDTO>> getExamsByCourseCode(@PathVariable final String course_code){
        return new ResponseEntity<>(IexamServices.getExamsByCourseCode(course_code),HttpStatus.OK);
    }
}
