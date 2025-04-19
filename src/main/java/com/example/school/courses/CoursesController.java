package com.example.school.courses;

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
@RequestMapping("/api/v1/courses")
public class CoursesController {
    
    private final ICoursesServices IcoursesServices;

    @Autowired
    public CoursesController(final ICoursesServices IcoursesServices){
        this.IcoursesServices = IcoursesServices;
    }

    @PostMapping(path = "/add")
    public ResponseEntity<List<CoursesDTO>> addCourses(@RequestBody final List<CoursesDTO> coursesDTOS){
        System.out.println(coursesDTOS);
        final List<CoursesDTO> savedCourses = IcoursesServices.addManyCourses(coursesDTOS);
        return new ResponseEntity<>(savedCourses,HttpStatus.CREATED);
    } 

    @GetMapping(path = "/{id}")
    public ResponseEntity<CoursesDTO> getCourseById(@PathVariable final Long id){
        Optional<CoursesDTO> foundedCourse = IcoursesServices.findCourseById(id);
        
        return foundedCourse.map(coursesDTO -> new ResponseEntity<>(coursesDTO, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/getCoursesByName/{course_name}")
    public ResponseEntity<CoursesDTO> getCoursesByName(@PathVariable String course_name){
        return new ResponseEntity<>(IcoursesServices.getCoursesByName(course_name),HttpStatus.OK);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<CoursesDTO>> getAllCourses(){
        return new ResponseEntity<>(IcoursesServices.getAllCourses(),HttpStatus.OK);
    }

    @PutMapping(path = "update/{id}")
    public ResponseEntity<CoursesDTO> updateCourse(@PathVariable final Long id, @RequestBody final CoursesDTO coursesDTO){
        final boolean isExist = IcoursesServices.isCoursesExists(coursesDTO);

        if (isExist) {
            final CoursesDTO updateCourses = IcoursesServices.updateCourses(id, coursesDTO);
            return new ResponseEntity<>(updateCourses,HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CoursesDTO> deleteCourse(@PathVariable final Long id){
        IcoursesServices.deleteCourses(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/getAllCoursesByDepartment/{department}")
    public ResponseEntity<List<CoursesDTO>> getAllCoursesByDepartment(@PathVariable final String department){
        return new ResponseEntity<>(IcoursesServices.getAllCoursesByDepartment(department),HttpStatus.OK);
    }

    @GetMapping("/getAllCoursesByTeacherId/{id}")
    public ResponseEntity<List<CoursesDTO>> getAllCoursesByTeacher_id(@PathVariable final Long id){
        return new ResponseEntity<>(IcoursesServices.getAllCoursesByTeacher_id(id),HttpStatus.OK);
    }

    @GetMapping(path = "/getAllCoursesByTeacherCode/{teacher_code}")
    public ResponseEntity<List<CoursesDTO>> getAllCoursesByTeacherCode(@PathVariable final String teacher_code){
        return new ResponseEntity<>(IcoursesServices.getAllCoursesByTeacherCode(teacher_code),HttpStatus.OK);
    }

    @GetMapping(path = "/getAllCoursesByStudentId/{student_id}")
    public ResponseEntity<List<CoursesDTO>> getAllCoursesByStudentId(@PathVariable final Long student_id){
        return new ResponseEntity<>(IcoursesServices.getAllCoursesByStudentId(student_id),HttpStatus.OK);
    }
    @GetMapping(path = "/getAllCoursesByStudentCode/{student_code}")
    public ResponseEntity<List<CoursesDTO>> getAllCoursesByStudentCode(@PathVariable final String student_code){
        return new ResponseEntity<>(IcoursesServices.getAllCoursesByStudentCode(student_code),HttpStatus.OK);
    }

    @GetMapping(path = "/getAllCoursesByResultsId/{result_id}")
    public ResponseEntity<List<CoursesDTO>> getAllCoursesByResultsId(@PathVariable final Long result_id){
        return new ResponseEntity<>(IcoursesServices.getAllCoursesByResultsId(result_id),HttpStatus.OK);
    }

    @GetMapping(path = "/getAllCoursesByAssignmentId/{assignment_id}")
    public ResponseEntity<List<CoursesDTO>> getAllCoursesByAssignmentId(@PathVariable final Long assignment_id){
        return new ResponseEntity<>(IcoursesServices.getAllCoursesByAssignmentId(assignment_id),HttpStatus.OK);
    }

    @GetMapping(path = "/getAllCoursesByClassesId/{class_id}")
    public ResponseEntity<List<CoursesDTO>> getAllCoursesByClassesId(@PathVariable final Long class_id){
        return new ResponseEntity<>(IcoursesServices.getAllCoursesByClassesId(class_id),HttpStatus.OK);
    }

    @GetMapping(path = "/getAllCoursesByClassesName/{class_name}")
    public ResponseEntity<List<CoursesDTO>> getAllCoursesByClassesName(@PathVariable final String class_name){
        return new ResponseEntity<>(IcoursesServices.getAllCoursesByClassesName(class_name),HttpStatus.OK);
    }
}
