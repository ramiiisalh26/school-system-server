package com.example.school.result;

public class ResultMapper {
    public static Result fromDTOToEntity(ResultDTO resultDTO){
        return Result.builder()
            .id(resultDTO.getId())
            .subjects(resultDTO.getSubjects())
            .classes(resultDTO.getClasses())
            .teacher(resultDTO.getTeacher())
            .date(resultDTO.getDate())
            .type(resultDTO.getType())
            .score(resultDTO.getScore())
            .build();
    }

    public static ResultDTO fromEntityToDTO(Result result){
        return ResultDTO.builder()
            .id(result.getId())
            .subjects(result.getSubjects())
            .classes(result.getClasses())
            .teacher(result.getTeacher())
            .date(result.getDate())
            .type(result.getType())
            .score(result.getScore())
            .build();
    }
}
