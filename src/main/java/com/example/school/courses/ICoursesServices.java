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

    CoursesDTO getCoursesByName(String courseName);

    List<CoursesDTO> getAllCoursesByDepartment(String department);

    List<CoursesDTO> getAllCoursesByTeacher_id(Long id);

    List<CoursesDTO> getAllCoursesByTeacherCode(String teacher_code);

    List<CoursesDTO> getAllCoursesByStudentId(Long student_id);

    List<CoursesDTO> getAllCoursesByStudentCode(String student_code);

    List<CoursesDTO> getAllCoursesByResultsId(Long result_id);

    List<CoursesDTO> getAllCoursesByAssignmentId(Long assignment_id);

    List<CoursesDTO> getAllCoursesByClassesId(Long class_id);

    List<CoursesDTO> getAllCoursesByClassesName(String class_name);
}
