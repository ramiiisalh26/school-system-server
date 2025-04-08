package com.example.school.parent;

import java.util.List;
import java.util.Optional;

import com.example.school.student.StudentDTO;
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
@RequestMapping(path = "/api/v1/parent")
public class ParentController {
    
    private final IParentServices parentServices;

    @Autowired
    public ParentController(final IParentServices parentServices){
        this.parentServices = parentServices;
    }

    @PostMapping(path = "/add")
    public ResponseEntity<List<ParentDTO>> addParent(@RequestBody final List<ParentDTO> parentsDTO) throws Exception{
        final List<ParentDTO> savedParents = parentServices.addManyParent(parentsDTO);
        return new ResponseEntity<List<ParentDTO>>(savedParents,HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ParentDTO> getParentById(@PathVariable final Long id){
        Optional<ParentDTO> parent = parentServices.getParentById(id);
        return parent.map(parentDTO -> new ResponseEntity<>(parentDTO, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<ParentDTO>> getAllParent(){
        return new ResponseEntity<List<ParentDTO>>(parentServices.getAllParent(),HttpStatus.OK);
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<ParentDTO> updateParent(@PathVariable final Long id, @RequestBody final ParentDTO parentDTO){
        parentDTO.setId(id);
        final boolean isExists = parentServices.isExists(parentDTO);
        if (isExists) {
            ParentDTO updatedParent = parentServices.updateParent(id, parentDTO);
            return new ResponseEntity<ParentDTO>(updatedParent,HttpStatus.OK);
        }
        return new ResponseEntity<ParentDTO>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<ParentDTO> deleteParent(@PathVariable final Long id){
        parentServices.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/getStudentByParentId/{id}")
    public  ResponseEntity<List<StudentDTO>> getStudentByParentId(@PathVariable final Long parentId){
        return new ResponseEntity<>(parentServices.getStudentByParentId(parentId),HttpStatus.OK);
    }
}
