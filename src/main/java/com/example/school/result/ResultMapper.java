package com.example.school.result;

import com.example.school.classes.ClassesMapper;
import com.example.school.student.StudentMapper;
import com.example.school.courses.CoursesMapper;
import com.example.school.teacher.TeacherMapper;

public class ResultMapper {
//    public static Result fromDTOToEntity(ResultDTO resultDTO){
//        return Result.builder()
//                .id(resultDTO.getId())
//                .courses(CoursesMapper.fromDTOToEntity(resultDTO.getCourse()))
//                .student(StudentMapper.fromDTOToEntity(resultDTO.getStudent_id()))
//                .teacher(TeacherMapper.fromDTOToEntity(resultDTO.getTeacher()))
//                .classes(ClassesMapper.fromDTOToEntity(resultDTO.getClasses()))
//                .date(resultDTO.getDate())
//                .type(resultDTO.getType())
//                .score(resultDTO.getScore())
//                .build();
//    }

    public static ResultDTO fromEntityToDTO(Result result){
        return ResultDTO.builder()
                .id(result.getId())
                .student_code(result.getStudent().getStudent_id())
                .teacher_id(result.getTeacher().getTeacher_id())
                .course_code(result.getCourses().getCourse_code())
                .class_name(result.getClasses().getName())
                .date(result.getDate())
                .type(result.getType())
                .score(result.getScore())
                .build();
    }
}
