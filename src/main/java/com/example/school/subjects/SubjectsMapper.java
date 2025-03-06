package com.example.school.subjects;


public class SubjectsMapper {

    public static Subjects fromDTOToEntity(SubjectsDTO subjectsDTO){
        return Subjects.builder()
            .id(subjectsDTO.getId() == null ? null : subjectsDTO.getId())
            .name(subjectsDTO.getName())
            .build();
    }

    public static SubjectsDTO fromEntityToDTO(Subjects subjects){
        return SubjectsDTO.builder()
            .id(subjects.getId() == null ? null : subjects.getId())
            .name(subjects.getName())
            .build();
    }
}
