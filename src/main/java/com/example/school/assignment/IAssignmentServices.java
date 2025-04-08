package com.example.school.assignment;

import java.util.List;
import java.util.Optional;

public interface IAssignmentServices {
    
    Boolean isExist(AssignmentDTO assignmentDTO);

    List<AssignmentDTO> getAllAssignment();

    List<AssignmentDTO> addManyAssignment(List<AssignmentDTO> assignmentsDTO);

    void addAssignment(AssignmentDTO assignmentDTO);

    Optional<AssignmentDTO> getAssignmentById(Long id);

    AssignmentDTO updateAssignment(Long id, AssignmentDTO assignmentDTO);

    void deleteAssignmentById(Long id);

    List<AssignmentDTO> getAssignmentsByTeacherCode(String teacher_code);

    List<AssignmentDTO> getAssignmentsByCoursesCode(String courses_code);

    List<AssignmentDTO> getAssignmentsByStudentCode(String student_code);
}
