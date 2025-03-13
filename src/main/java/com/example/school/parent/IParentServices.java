package com.example.school.parent;

import com.example.school.student.StudentDTO;

import java.util.List;
import java.util.Optional;

public interface IParentServices {
    
    Boolean isExists(ParentDTO parentDTO);

    List<ParentDTO> getAllParent();

    List<ParentDTO> addManyParent(List<ParentDTO> parentsDTO) throws Exception;

    void addParent(ParentDTO parentDTO) throws Exception ;

    ParentDTO updateParent(Long id, ParentDTO parentDTO);

    Optional<ParentDTO> getParentById(Long Id);

    void deleteById(Long id);

    List<StudentDTO> getStudentByParentId(Long id);

}
