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
    
    private ILessonServices lessonServices;

    @Autowired
    public LessonController(final ILessonServices lessonServices){
        this.lessonServices = lessonServices;
    }

    @PostMapping(path = "/add")
    public ResponseEntity<List<LessonDTO>> addManyLessons(@RequestBody final List<LessonDTO> lessonDTO){
        List<LessonDTO> savedLessons = lessonServices.addManyLessons(lessonDTO);
        return new ResponseEntity<List<LessonDTO>>(savedLessons,HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<LessonDTO> getLessonById(@PathVariable final Long id){
        Optional<LessonDTO> lesson = lessonServices.getLessonById(id);
        if (lesson.isPresent()) {
            return new ResponseEntity<LessonDTO>(lesson.get(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<LessonDTO>> getAllLessons(){
        return new ResponseEntity<List<LessonDTO>>(lessonServices.getAllLessons(),HttpStatus.OK);
    } 

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<LessonDTO> updateLesson(@PathVariable final Long id,@RequestBody final LessonDTO lessonDTO){
        final boolean isExist = lessonServices.isExist(lessonDTO);
        if (isExist) {
            LessonDTO updateLesson = lessonServices.updateLessons(id, lessonDTO);
            return new ResponseEntity<LessonDTO>(updateLesson,HttpStatus.OK);
        }
        return new ResponseEntity<LessonDTO>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<LessonDTO> deleteLesson(@PathVariable final Long id){
        lessonServices.deleteLessoyId(id);
        return new ResponseEntity<LessonDTO>(HttpStatus.OK);
    }
}
