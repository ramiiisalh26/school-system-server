package com.example.school.assignment;

public class AssignmentMapper {
    public static Assignment fromDTOToEntity(AssignmentDTO assignmentDTO){
        return Assignment.builder()
            .id(assignmentDTO.getId())
            .courses(assignmentDTO.getSubjects())
            .classes(assignmentDTO.getClasses())
            .teacher(assignmentDTO.getTeacher())
            .dueDate(assignmentDTO.getDueDate())
            .build();
    }
    public static AssignmentDTO fromEntityToDTO(Assignment assignment){
        return AssignmentDTO.builder()
            .id(assignment.getId())
            .subjects(assignment.getCourses())
            .classes(assignment.getClasses())
            .teacher(assignment.getTeacher())
            .dueDate(assignment.getDueDate())
            .build();
    }
}
