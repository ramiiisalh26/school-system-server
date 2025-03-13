package com.example.school.student;

import java.util.List;
import java.util.Optional;

import com.example.school.classes.ClassesDTO;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
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
//@AllArgsConstructor
//@RequiredArgsConstructor
public class StudentController {

    private IStudentServices IstudentServices;

    @Autowired
    public StudentController(final IStudentServices IstudentServices){
        this.IstudentServices = IstudentServices;
    }

    public StudentController(){}

    @PostMapping(path = "/add")
    public ResponseEntity<List<StudentDTO>> addStudent(@RequestBody final List<StudentDTO> studentDTO){
        List<StudentDTO> studentDTOs = IstudentServices.addManyStudent(studentDTO);
        return new ResponseEntity<>(studentDTOs,HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable final Long id){
        Optional<StudentDTO> studentDTO = IstudentServices.getStudentById(id);
        return studentDTO.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<StudentDTO>> getAllStudent(){
        return new ResponseEntity<>(IstudentServices.getAllStudent(),HttpStatus.OK);
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable final Long id,@RequestBody final StudentDTO studentDTO){
        final boolean isExists = IstudentServices.isExists(studentDTO);
        if (isExists) {
            final StudentDTO updatedStudent = IstudentServices.updateStudentById(id, studentDTO);
            return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<StudentDTO> deleteStudent(@PathVariable final Long id){
        IstudentServices.deleteStudentById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/getStudentClasses/{id}")
    public ResponseEntity<List<ClassesDTO>> getStudentClasses(@PathVariable final Long id){
        List<ClassesDTO> classes = IstudentServices.getStudentClasses(id);
        return new ResponseEntity<>(classes, HttpStatus.OK);
    }
}
