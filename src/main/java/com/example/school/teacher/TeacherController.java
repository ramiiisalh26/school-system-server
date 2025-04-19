package com.example.school.teacher;

import java.util.List;
import java.util.Optional;

import com.example.school.classes.ClassesDTO;
import com.example.school.courses.CoursesDTO;
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
@RequestMapping("api/v1/teacher")
public class TeacherController {
    
    private ITeacherServices teacherServices;

    @Autowired
    public TeacherController(final ITeacherServices teacherServices){
        this.teacherServices = teacherServices;
    }

    public  TeacherController(){}

    @PostMapping(path = "/add")
    public ResponseEntity<List<TeacherDTO>> addTeacher(@RequestBody final List<TeacherDTO> teachersDTO){
        return new ResponseEntity<List<TeacherDTO>>(teacherServices.addManyTeacher(teachersDTO),HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<TeacherDTO> getTeacherById(@PathVariable final Long id){
        Optional<TeacherDTO> teacher = teacherServices.getTeacherById(id);
        if (teacher.isPresent()) {
            return new ResponseEntity<TeacherDTO>(teacher.get(),HttpStatus.OK);            
        }

        return new ResponseEntity<TeacherDTO>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<TeacherDTO>> getAllTeacher(){
        return new ResponseEntity<List<TeacherDTO>>(teacherServices.getAllTeacher(),HttpStatus.OK);
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<TeacherDTO> updateTeacher(@PathVariable final Long id, @RequestBody final TeacherDTO teacherDTO){
        final boolean isExist = teacherServices.isExists(teacherDTO);
        if (isExist) {
            final TeacherDTO updatedTeacher = teacherServices.updateTeacher(id, teacherDTO);
            return new ResponseEntity<TeacherDTO>(updatedTeacher,HttpStatus.OK);
        }
        return new ResponseEntity<TeacherDTO>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<TeacherDTO> deleteTeacher(@PathVariable final Long id){
        teacherServices.deleteTeacher(id);
        return new ResponseEntity<TeacherDTO>(HttpStatus.OK);
    }

    @GetMapping(path = "/getTeacherSubjects/{id}")
    public ResponseEntity<List<CoursesDTO>> getTeacherSubjects(@PathVariable final Long id){
        return new ResponseEntity<>(teacherServices.getTeacherCourses(id),HttpStatus.OK);
    }

    @GetMapping(path = "/getTeacherClasses")
    public  ResponseEntity<List<ClassesDTO>> getTeacherClasses(Long id){
        return new ResponseEntity<>(teacherServices.getTeacherClasses(id),HttpStatus.OK);
    }
}