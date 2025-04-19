package com.example.school.classes;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;
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
        return new ResponseEntity<>(classesServices.getAllClasses(),HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<ClassesDTO> deleteClasses(@PathVariable final Long id){
        classesServices.deleteClassesById(id);
        return new ResponseEntity<ClassesDTO>(HttpStatus.OK);
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<ClassesDTO> updateClass(@PathVariable final Long id , @RequestBody final ClassesDTO classesDTO){
        final boolean isExist = classesServices.isClassExists(classesDTO);
        if (isExist) {
            final ClassesDTO updateClass = classesServices.updateClass(id, classesDTO);
            return new ResponseEntity<ClassesDTO>(updateClass,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/getClassByName/{class_name}")
    public ResponseEntity<ClassesDTO> getClassesByName(@PathVariable final String class_name){
        return new ResponseEntity<>(classesServices.getClassesByName(class_name),HttpStatus.OK);
    }

    @GetMapping(path = "/getClassesByTeacherId/{teacher_id}")
    public ResponseEntity<List<ClassesDTO>> getClassesByTeacherId(@PathVariable final Long teacher_id){
        System.out.println(teacher_id);

        return new ResponseEntity<>(classesServices.getClassesByTeacherId(teacher_id), HttpStatus.OK);
    }

    @GetMapping(path = "/getClassesByTeacherCode/{teacher_code}")
    public ResponseEntity<List<ClassesDTO>> getClassesByTeacherCode(@PathVariable final String teacher_code){
        return new ResponseEntity<>(classesServices.getClassesByTeacher_code(teacher_code),HttpStatus.OK);
    }

    @GetMapping(path = "/getClassesByCourseCode/{course_code}")
    public ResponseEntity<List<ClassesDTO>> getClassesByCoursesCode(@PathVariable final String course_code){
        return new ResponseEntity<>(classesServices.getClassesByCourses_code(course_code),HttpStatus.OK);
    }

    @GetMapping(path = "/getClassesByStudentId/{studentId}")
    public ResponseEntity<List<ClassesDTO>> getClassesByStudentId(@PathVariable final Long studentId){
        return new ResponseEntity<>(classesServices.getClassesByStudent_id(studentId),HttpStatus.OK);
    }

    @GetMapping(path = "/getClassesByStudentCode/{studentCode}")
    public ResponseEntity<List<ClassesDTO>> getClassesByStudentCode(@PathVariable final String studentCode){
        return new ResponseEntity<>(classesServices.getClassesByStudent_code(studentCode),HttpStatus.OK);
    }

    @GetMapping(path = "/getClassesByResultsId/{resultId}")
    public ResponseEntity<List<ClassesDTO>> getClassesByResultsId(@PathVariable final Long resultId){
        return new ResponseEntity<>(classesServices.getClassesByResults_id(resultId),HttpStatus.OK);
    }

}
