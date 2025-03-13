package com.example.school.result;

import com.example.school.student.StudentMapper;
import com.example.school.courses.CoursesMapper;
import com.example.school.teacher.TeacherMapper;

public class ResultMapper {
    public static Result fromDTOToEntity(ResultDTO resultDTO){
        return Result.builder()
            .id(resultDTO.getId())
            .subjects(CoursesMapper.fromDTOToEntity(resultDTO.getCoursesDTO()))
            .student(StudentMapper.fromDTOToEntity(resultDTO.getStudentDTO()))
            .teacher(TeacherMapper.fromDTOToEntity(resultDTO.getTeacherDTO()))
            .date(resultDTO.getDate())
            .type(resultDTO.getType())
            .score(resultDTO.getScore())
            .build();
    }

    public static ResultDTO fromEntityToDTO(Result result){
        return ResultDTO.builder()
            .id(result.getId())
            .coursesDTO(CoursesMapper.fromEntityToDTO(result.getSubjects()))
            .studentDTO(StudentMapper.fromEntityToDTO(result.getStudent()))
            .teacherDTO(TeacherMapper.fromEntityToDTO(result.getTeacher()))
            .date(result.getDate())
            .type(result.getType())
            .score(result.getScore())
            .build();
    }
}
