package com.example.school.Courses;

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
public class CoursesController {
    
    private final ICoursesServices IcoursesServices;

    @Autowired
    public CoursesController(final ICoursesServices IcoursesServices){
        this.IcoursesServices = IcoursesServices;
    }

    @PostMapping(path = "/add")
    public ResponseEntity<List<CoursesDTO>> addSubjects(@RequestBody final List<CoursesDTO> coursesDTOS){
        System.out.println(coursesDTOS);
        final List<CoursesDTO> savedSujects = IcoursesServices.addManyCourses(coursesDTOS);
        return new ResponseEntity<List<CoursesDTO>>(savedSujects,HttpStatus.CREATED);
    } 

    @GetMapping(path = "/{id}")
    public ResponseEntity<CoursesDTO> getSubjectById(@PathVariable final Long id){
        Optional<CoursesDTO> foundedSubject = IcoursesServices.findCourseById(id);

        if (foundedSubject.isPresent()) {
            return new ResponseEntity<CoursesDTO>(foundedSubject.get(),HttpStatus.OK);
        }

        return new ResponseEntity<CoursesDTO>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<CoursesDTO>> getAllSubjects(){
        return new ResponseEntity<List<CoursesDTO>>(IcoursesServices.getAllCourses(),HttpStatus.OK);
    }

    @PutMapping(path = "update/{id}")
    public ResponseEntity<CoursesDTO> updateSubject(@PathVariable final Long id, @RequestBody final CoursesDTO coursesDTO){
        final boolean isExist = IcoursesServices.isCoursesExists(coursesDTO);

        if (isExist) {
            final CoursesDTO updateSubjects = IcoursesServices.updateCourses(id, coursesDTO);
            return new ResponseEntity<CoursesDTO>(updateSubjects,HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CoursesDTO> deleteSubject(@PathVariable final Long id){
        IcoursesServices.deleteCourses(id);
        return new ResponseEntity<CoursesDTO>(HttpStatus.OK);
    }

    @GetMapping("/getAllSujectsByTeacher_id/{id}")
    public ResponseEntity<List<CoursesDTO>> getAllSujectsByTeacher_id(@PathVariable final Long id){
        return new ResponseEntity<List<CoursesDTO>>(IcoursesServices.getAllSujectsByTeacher_id(id),HttpStatus.OK);
    }

}
