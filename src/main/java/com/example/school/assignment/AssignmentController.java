package com.example.school.assignment;

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
@RequestMapping("/api/v1/assignment")
public class AssignmentController {
    
    private final IAssignmentServices assignmentServices;

    @Autowired
    public AssignmentController(final IAssignmentServices assignmentServices){
        this.assignmentServices = assignmentServices;
    }

    @PostMapping(path = "/add")
    public ResponseEntity<List<AssignmentDTO>> addManyAssignment(@RequestBody final List<AssignmentDTO> assignmentDTO){
        final List<AssignmentDTO> savedAssignment = assignmentServices.addManyAssignment(assignmentDTO);
        return new ResponseEntity<List<AssignmentDTO>>(savedAssignment,HttpStatus.CREATED);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<AssignmentDTO>> getAllAssignment(){
        return new ResponseEntity<List<AssignmentDTO>>(assignmentServices.getAllAssignment(),HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<AssignmentDTO> getAssignmentById(@PathVariable final Long id){
        final Optional<AssignmentDTO> isAssignmentExist = assignmentServices.getAssignmentById(id);
        return isAssignmentExist.map(assignmentDTO -> new ResponseEntity<>(assignmentDTO, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<AssignmentDTO> updateAssignment(@PathVariable final Long id, @RequestBody final AssignmentDTO assignmentDTO){
        final Boolean assignmentIsExist = assignmentServices.isExist(assignmentDTO);
        if (assignmentIsExist) {
            final AssignmentDTO updateAssignment = assignmentServices.updateAssignment(id, assignmentDTO);
            return new ResponseEntity<AssignmentDTO>(updateAssignment,HttpStatus.OK);
        }
        return new ResponseEntity<AssignmentDTO>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<AssignmentDTO> deleteAssignmentById(@PathVariable final Long id){
        assignmentServices.deleteAssignmentById(id);
        return new ResponseEntity<AssignmentDTO>(HttpStatus.OK);
    }

    @GetMapping(path = "/getByTeacherCode/{teacher_code}")
    public ResponseEntity<List<AssignmentDTO>> getAllAssignmentByTeacherCode(@PathVariable final String teacher_code){
        List<AssignmentDTO> assignments = assignmentServices.getAssignmentsByTeacherCode(teacher_code);
        return new ResponseEntity<>(assignments,HttpStatus.OK);
    }

    @GetMapping(path = "/getByStudentCode/{student_code}")
    public ResponseEntity<List<AssignmentDTO>> getAllAssignmentByStudentCode(@PathVariable final String student_code){
        List<AssignmentDTO> assignments = assignmentServices.getAssignmentsByStudentCode(student_code);
        return new ResponseEntity<>(assignments,HttpStatus.OK);
    }

    @GetMapping(path = "/getByCourseCode/{course_code}")
    public ResponseEntity<List<AssignmentDTO>> getAllAssignmentByCourseCode(@PathVariable final String course_code){
        List<AssignmentDTO> assignmentS = assignmentServices.getAssignmentsByCoursesCode(course_code);
        return new ResponseEntity<>(assignmentS,HttpStatus.OK);
    }

}
