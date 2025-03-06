package com.example.school.teacher;

import java.util.List;
import java.util.Optional;

public interface ITeacherServices {

    Boolean isExists(TeacherDTO teacherDTO);

    List<TeacherDTO> addManyTeacher(List<TeacherDTO> teachersDTO);

    TeacherDTO addTeacher(TeacherDTO teacherDTO);

    Optional<TeacherDTO> getTeacherById(Long id);

    List<TeacherDTO> getAllTeacher();

    TeacherDTO updateTeacher(Long id, TeacherDTO teacherDTO);

    void deleteTeacher(Long id);

    TeacherDTO addTeacherSubjects();

    // void setAddressIdToBeNull(Long id);
}
