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
    
    private IResultServices resultServices;

    @Autowired
    public ResultController(final IResultServices resultServices){
        this.resultServices = resultServices;
    }

    @PostMapping(path = "/add")
    public ResponseEntity<List<ResultDTO>> addResult(@RequestBody final List<ResultDTO> resultDTO){
        final List<ResultDTO> savedResult = resultServices.addManyResult(resultDTO);
        return new ResponseEntity<List<ResultDTO>>(savedResult,HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ResultDTO> getResult(@PathVariable final Long id){
        Optional<ResultDTO> result = resultServices.getResultById(id);
        return new ResponseEntity<ResultDTO>(result.get(),HttpStatus.OK);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<ResultDTO>> getAllResults(){
        return new ResponseEntity<List<ResultDTO>>(resultServices.getAllResults(),HttpStatus.OK);
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<ResultDTO> updateResult(@PathVariable final Long id,@RequestBody final ResultDTO resultDTO){
        final Boolean isExist = resultServices.isExist(resultDTO);
        if (isExist) {
            final ResultDTO updatedResult = resultServices.updateResult(id, resultDTO);
            return new ResponseEntity<ResultDTO>(updatedResult,HttpStatus.OK);
        }
        return new ResponseEntity<ResultDTO>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<ResultDTO> deleteResultById(@PathVariable final Long id){
        resultServices.deleteResultById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
