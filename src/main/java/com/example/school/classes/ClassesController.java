package com.example.school.classes;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/classes")
public class ClassesController {
    
    private IClassesServices classesServices;

    public ClassesController(IClassesServices classesServices){
        this.classesServices = classesServices;
    }

    @PostMapping(path = "/create")
    public ResponseEntity<List<ClassesDTO>> createClass(@RequestBody final List<ClassesDTO> classesDTO){
        final List<ClassesDTO> savedClasses = classesServices.createManyClass(classesDTO);
        return new ResponseEntity<List<ClassesDTO>>(savedClasses,HttpStatus.CREATED); 
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ClassesDTO> getClassById(@PathVariable final Long id){
        Optional<ClassesDTO> foundedClass = classesServices.findById(id);

        return foundedClass
            .map(classes -> new ResponseEntity<ClassesDTO>(classes,HttpStatus.OK))
            .orElse(new ResponseEntity<ClassesDTO>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<ClassesDTO>> getAllClasses(){
        return new ResponseEntity<List<ClassesDTO>>(classesServices.getAllClasses(),HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<ClassesDTO> deleteClass(@PathVariable final Long id){
        classesServices.deleteClassbyId(id);
        return new ResponseEntity<ClassesDTO>(HttpStatus.OK);
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<ClassesDTO> updateClass(@PathVariable final Long id , @RequestBody final ClassesDTO classesDTO){
        final boolean isExsit = classesServices.isClassExists(classesDTO);
        if (isExsit) {
            final ClassesDTO updateClass = classesServices.updateClass(id, classesDTO);
            return new ResponseEntity<ClassesDTO>(updateClass,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/getClassesByTeacher_id")
    public ResponseEntity<List<ClassesDTO>> getClassesByTeacherId(@RequestParam final Long id){
        System.out.println(id);
        return new ResponseEntity<List<ClassesDTO>>(classesServices.getClassesByTeacherId(id), HttpStatus.OK);
    }
}
