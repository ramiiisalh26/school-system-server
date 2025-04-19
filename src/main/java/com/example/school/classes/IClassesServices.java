package com.example.school.classes;

import java.util.List;
import java.util.Optional;

public interface IClassesServices {

    Boolean isClassExists(ClassesDTO classesDTO);

    ClassesDTO createClass(ClassesDTO classesDTO);

    List<ClassesDTO> createManyClass(List<ClassesDTO> classesDTOs);

    Optional<ClassesDTO> findById(Long id);

    ClassesDTO getClassesByName(String name);

    List<ClassesDTO> getAllClasses();

    void deleteClassesById(Long id);

    ClassesDTO updateClass(Long id, ClassesDTO classesDTO);
    
    List<ClassesDTO> getClassesByTeacherId(Long teacher_id);

    List<ClassesDTO> getClassesByTeacher_code(String teacher_code);

    List<ClassesDTO> getClassesByCourses_code(String course_code);

    List<ClassesDTO> getClassesByStudent_id(Long student_id);

    List<ClassesDTO> getClassesByStudent_code(String student_code);

    List<ClassesDTO> getClassesByResults_id(Long result_id);

//    void setTeacherIdToBeNull(Long id);

}
