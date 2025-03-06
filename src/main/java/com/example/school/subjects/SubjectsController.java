package com.example.school.subjects;

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
@RequestMapping("/api/v1/subjects")
public class SubjectsController {
    
    private ISubjectsServices subjectsServices;

    @Autowired
    public SubjectsController(final ISubjectsServices subjectsServices){
        this.subjectsServices = subjectsServices;
    }

    @PostMapping(path = "/add")
    public ResponseEntity<List<SubjectsDTO>> addSubjects(@RequestBody final List<SubjectsDTO> subjectsDTOs){
        System.out.println(subjectsDTOs);
        final List<SubjectsDTO> savedSujects = subjectsServices.addManySubject(subjectsDTOs);
        return new ResponseEntity<List<SubjectsDTO>>(savedSujects,HttpStatus.CREATED);
    } 

    @GetMapping(path = "/{id}")
    public ResponseEntity<SubjectsDTO> getSubjectById(@PathVariable final Long id){
        Optional<SubjectsDTO> foundedSubject = subjectsServices.findSubjectById(id);

        if (foundedSubject.isPresent()) {
            return new ResponseEntity<SubjectsDTO>(foundedSubject.get(),HttpStatus.OK);
        }

        return new ResponseEntity<SubjectsDTO>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<SubjectsDTO>> getAllSubjects(){
        return new ResponseEntity<List<SubjectsDTO>>(subjectsServices.getAllSubjects(),HttpStatus.OK);
    }

    @PutMapping(path = "update/{id}")
    public ResponseEntity<SubjectsDTO> updateSubject(@PathVariable final Long id, @RequestBody final SubjectsDTO subjectsDTO){
        final boolean isExist = subjectsServices.isSubjectExists(subjectsDTO);

        if (isExist) {
            final SubjectsDTO updateSubjects = subjectsServices.updateSubject(id, subjectsDTO);
            return new ResponseEntity<SubjectsDTO>(updateSubjects,HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SubjectsDTO> deleteSubject(@PathVariable final Long id){
        subjectsServices.deleteSubject(id);
        return new ResponseEntity<SubjectsDTO>(HttpStatus.OK);
    }


}
