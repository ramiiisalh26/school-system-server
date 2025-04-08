package com.example.school.result;

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
@RequestMapping("/api/v1/result")
public class ResultController {
    
    private final IResultServices IresultServices;

    @Autowired
    public ResultController(final IResultServices resultServices){
        this.IresultServices = resultServices;
    }

    @PostMapping(path = "/add")
    public ResponseEntity<List<ResultDTO>> addResult(@RequestBody final List<ResultDTO> resultDTO){
        final List<ResultDTO> savedResult = IresultServices.addManyResult(resultDTO);
        return new ResponseEntity<>(savedResult,HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ResultDTO> getResult(@PathVariable final Long id){
        Optional<ResultDTO> result = IresultServices.getResultById(id);
        return result.map(resultDTO -> new ResponseEntity<>(resultDTO, HttpStatus.OK)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<ResultDTO>> getAllResults(){
        return new ResponseEntity<>(IresultServices.getAllResults(),HttpStatus.OK);
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<ResultDTO> updateResult(@PathVariable final Long id,@RequestBody final ResultDTO resultDTO){
        final Boolean isExist = IresultServices.isExist(resultDTO);
        if (isExist) {
            final ResultDTO updatedResult = IresultServices.updateResult(id, resultDTO);
            return new ResponseEntity<>(updatedResult,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<ResultDTO> deleteResultById(@PathVariable final Long id){
        IresultServices.deleteResultById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "getResultByTeacherId/{id}")
    public ResponseEntity<List<ResultDTO>> getResultByTeacherId(@PathVariable final Long id){
        return new ResponseEntity<>(IresultServices.getResultsByTeacherId(id),HttpStatus.OK);
    }

    @GetMapping(path = "getResultByTeacherCode/{teacherCode}")
    public ResponseEntity<List<ResultDTO>> getResultByTeacherCode(@PathVariable final String teacherCode){
        return new ResponseEntity<>(IresultServices.getResultByTeacherCode(teacherCode),HttpStatus.OK);
    }

    @GetMapping(path = "getResultByStudentId/{id}")
    public ResponseEntity<List<ResultDTO>> getResultByStudentId(@PathVariable final Long id){
        return new ResponseEntity<>(IresultServices.getResultsByStudentId(id),HttpStatus.OK);
    }

    @GetMapping(path = "getResultByStudentCode/{studentCode}")
    public ResponseEntity<List<ResultDTO>> getResultByStudentCode(@PathVariable final String studentCode){
        return new ResponseEntity<>(IresultServices.getResultsByStudentCode(studentCode),HttpStatus.OK);
    }

    @GetMapping(path = "getResultByCourseId/{id}")
    public ResponseEntity<List<ResultDTO>> getResultByCourseId(@PathVariable final Long id){
        return new ResponseEntity<>(IresultServices.getResultsByCourseId(id),HttpStatus.OK);
    }

    @GetMapping(path = "getResultByCourseCode/{courseCode}")
    public ResponseEntity<List<ResultDTO>> getResultByCourseCode(@PathVariable final String courseCode){
        return new ResponseEntity<>(IresultServices.getResultByCourseCode(courseCode),HttpStatus.OK);
    }
}
