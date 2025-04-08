package com.example.school.assignment;

public class AssignmentMapper {
//    public static Assignment fromDTOToEntity(AssignmentDTO assignmentDTO){
//        return Assignment.builder()
//            .id(assignmentDTO.getId())
//            .courses(assignmentDTO.getCourses())
//            .classes(assignmentDTO.getClasses())
//            .teacher(assignmentDTO.getTeacher())
//            .dueDate(assignmentDTO.getDueDate())
//            .build();
//    }
    public static AssignmentDTO fromEntityToDTO(Assignment assignment){
        return AssignmentDTO.builder()
                .id(assignment.getId())
                .course_code(assignment.getCourses().getCourse_code())
                .class_name(assignment.getClasses().getName())
                .teacher_code(assignment.getTeacher().getTeacher_id())
                .student_code(assignment.getStudent().getStudent_id())
                .dueDate(assignment.getDueDate())
                .build();
    }
}
