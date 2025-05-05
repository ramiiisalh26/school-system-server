package com.example.school.lesson;

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
@RequestMapping("/api/v1/lesson")
public class LessonController {
    
    private final ILessonServices IlessonServices;

    @Autowired
    public LessonController(final ILessonServices IlessonServices){
        this.IlessonServices = IlessonServices;
    }

    @PostMapping(path = "/add")
    public ResponseEntity<List<LessonDTO>> addManyLessons(@RequestBody final List<LessonDTO> lessonDTO){
        List<LessonDTO> savedLessons = IlessonServices.addManyLessons(lessonDTO);
        return new ResponseEntity<>(savedLessons,HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<LessonDTO> getLessonById(@PathVariable final Long id){
        Optional<LessonDTO> lesson = IlessonServices.getLessonById(id);
        return lesson.map(lessonDTO -> new ResponseEntity<>(lessonDTO, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<LessonDTO>> getAllLessons(){
        return new ResponseEntity<>(IlessonServices.getAllLessons(),HttpStatus.OK);
    } 

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<LessonDTO> updateLesson(@PathVariable final Long id,@RequestBody final LessonDTO lessonDTO){
        final boolean isExist = IlessonServices.isExist(lessonDTO);
        if (isExist) {
            LessonDTO updateLesson = IlessonServices.updateLessons(id, lessonDTO);
            return new ResponseEntity<>(updateLesson,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<LessonDTO> deleteLesson(@PathVariable final Long id){
        IlessonServices.deleteLessonById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/getLessonsByCourseCode/{course_code}")
    public ResponseEntity<List<LessonDTO>> getLessonsByCourseCode(@PathVariable final String course_code){
       List<LessonDTO> lessonDTOS = IlessonServices.getLessonByCourseCode(course_code);
       return new ResponseEntity<>(lessonDTOS,HttpStatus.OK);
    }

    @GetMapping(path = "/getLessonsByClassName/{class_name}")
    public ResponseEntity<List<LessonDTO>> getLessonsByClassName(@PathVariable final String class_name){
        List<LessonDTO> lessonDTOS = IlessonServices.getLessonByClassName(class_name);
        return new ResponseEntity<>(lessonDTOS,HttpStatus.OK);
    }

    @GetMapping(path = "/getLessonsByTeacherCode/{teacher_code}")
    public ResponseEntity<List<LessonDTO>> getLessonsByTeacherCode(@PathVariable final String teacher_code){
        List<LessonDTO> lessonDTOS = IlessonServices.getLessonByTeacherCode(teacher_code);
        return new ResponseEntity<>(lessonDTOS,HttpStatus.OK);
    }
}
