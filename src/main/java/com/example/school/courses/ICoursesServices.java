package com.example.school.courses;

import java.util.List;
import java.util.Optional;

public interface ICoursesServices {
    
    Boolean isCoursesExists(CoursesDTO coursesDTO);

    List<CoursesDTO> addManyCourses(List<CoursesDTO> coursesDTOS);

    CoursesDTO addCourse(CoursesDTO coursesDTO);

    Optional<CoursesDTO> findCourseById(Long id);

    List<CoursesDTO> getAllCourses();

    void deleteCourses(Long id);

    CoursesDTO updateCourses(Long id, CoursesDTO coursesDTO);

    CoursesDTO getCoursesByName(String subjectName);

    void deleteTeacher_subject(Long id);

    List<CoursesDTO> getAllSujectsByTeacher_id(Long id);
}
