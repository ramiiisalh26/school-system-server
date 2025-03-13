package com.example.school.courses;


public class CoursesMapper {

    public static Courses fromDTOToEntity(CoursesDTO coursesDTO){
        return Courses.builder()
            .id(coursesDTO.getId() == null ? null : coursesDTO.getId())
            .name(coursesDTO.getName())
            .build();
    }

    public static CoursesDTO fromEntityToDTO(Courses courses){
        return CoursesDTO.builder()
            .id(courses.getId() == null ? null : courses.getId())
            .name(courses.getName())
            .build();
    }
}
