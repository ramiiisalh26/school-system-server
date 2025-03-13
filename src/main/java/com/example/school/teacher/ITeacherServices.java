package com.example.school.teacher;

import com.example.school.classes.ClassesDTO;
import com.example.school.Courses.CoursesDTO;

import java.util.List;
import java.util.Optional;

public interface ITeacherServices {

    Boolean isExists(TeacherDTO teacherDTO);

    List<TeacherDTO> addManyTeacher(List<TeacherDTO> teachersDTO);

    void addTeacher(TeacherDTO teacherDTO);

    Optional<TeacherDTO> getTeacherById(Long id);

    List<TeacherDTO> getAllTeacher();

    TeacherDTO updateTeacher(Long id, TeacherDTO teacherDTO);

    void deleteTeacher(Long id);

//    TeacherDTO addTeacherSubjects();

    List<CoursesDTO> getTeacherCourses(Long id);

    List<ClassesDTO> getTeacherClasses(Long id);
    // void setAddressIdToBeNull(Long id);
}
