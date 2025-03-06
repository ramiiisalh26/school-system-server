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
    
    private IExamServices examServices;
    
    @Autowired
    public ExamController(final IExamServices examServices){
        this.examServices = examServices;
    }

    @PostMapping(path = "/add")
    public ResponseEntity<List<ExamDTO>> addExams(@RequestBody final List<ExamDTO> examsDTO){
        final List<ExamDTO> savedExams = examServices.addManyExams(examsDTO);
        return new ResponseEntity<List<ExamDTO>>(savedExams,HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ExamDTO> getExamyId(@PathVariable final Long id){
        final Optional<ExamDTO> examDTO = examServices.getExamById(id);
        if (examDTO.isPresent()) {
            return new ResponseEntity<ExamDTO>(examDTO.get(),HttpStatus.OK);
        }
        return new ResponseEntity<ExamDTO>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<ExamDTO>> getAllExams(){
        return new ResponseEntity<List<ExamDTO>>(examServices.getAllExams(),HttpStatus.OK);
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<ExamDTO> updateExam(@PathVariable final Long id, @RequestBody final ExamDTO examDTO){
        final boolean exam = examServices.isExist(examDTO);
        if (exam) {
            final ExamDTO updatedExam = examServices.updateExam(id, examDTO);
            return new ResponseEntity<ExamDTO>(updatedExam,HttpStatus.OK);
        }
        return new ResponseEntity<ExamDTO>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<ExamDTO> deleteExam(@PathVariable final Long id){
        examServices.deleteExam(id);
        return new ResponseEntity<ExamDTO>(HttpStatus.OK);
    }
}
