package com.example.school.student;

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
@RequestMapping("/api/v1/student")
public class StudentController {

    private IStudentServices studentServices;

    @Autowired
    public StudentController(final IStudentServices studentServices){
        this.studentServices = studentServices;
    }

    @PostMapping(path = "/add")
    public ResponseEntity<List<StudentDTO>> addStudent(@RequestBody final List<StudentDTO> studentDTO){
        List<StudentDTO> studentDTOs = studentServices.addManyStudent(studentDTO);
        return new ResponseEntity<List<StudentDTO>>(studentDTOs,HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable final Long id){
        Optional<StudentDTO> studentDTO = studentServices.getStudentById(id);
        if (studentDTO.isPresent()) {
            return new ResponseEntity<StudentDTO>(studentDTO.get(),HttpStatus.OK);
        }

        return new ResponseEntity<StudentDTO>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<StudentDTO>> getAllStudent(){
        return new ResponseEntity<List<StudentDTO>>(studentServices.getAllStudent(),HttpStatus.OK);
    }

    @PutMapping(path = "update/{id}")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable final Long id,@RequestBody final StudentDTO studentDTO){
        final boolean isExists = studentServices.isExists(studentDTO);
        if (isExists) {
            final StudentDTO updatedStudent = studentServices.updateStudentById(id, studentDTO);
            return new ResponseEntity<StudentDTO>(updatedStudent, HttpStatus.OK); 
        }
        return new ResponseEntity<StudentDTO>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StudentDTO> deleteStudent(@PathVariable final Long id){
        studentServices.deleteStudentById(id);
        return new ResponseEntity<StudentDTO>(HttpStatus.OK);
    }
}
