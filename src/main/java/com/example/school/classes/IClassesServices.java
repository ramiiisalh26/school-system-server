package com.example.school.classes;

import java.util.List;
import java.util.Optional;

public interface IClassesServices {

    Boolean isClassExists(ClassesDTO classesDTO);

    ClassesDTO createClass(ClassesDTO classesDTO);

    List<ClassesDTO> createManyClass(List<ClassesDTO> classesDTOs);

    Optional<ClassesDTO> findById(Long id);

    List<ClassesDTO> getAllClasses();

    void deleteClassbyId(Long id);

    ClassesDTO updateClass(Long id, ClassesDTO classesDTO);
    
    List<ClassesDTO> getClassesByTeacherId(Long tracher_id);
    
    ClassesDTO getClassesByName(String name);

    void setTeacherIdToBeNull(Long id);

}
