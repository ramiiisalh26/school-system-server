package com.example.school.classes;

public class ClassesMapper {
    
    public static Classes fromDTOToEntity(ClassesDTO classesDTO){
        return Classes.builder()
            .id(classesDTO.getId() == null ? null : classesDTO.getId())
            .name(classesDTO.getName())
            .capacity(classesDTO.getCapacity())
            .grade(classesDTO.getGrade())
            .super_visor(classesDTO.getSuper_visor())
            // .teacher(classesDTO.getTeacher_id())
            .build();
    }

    public static ClassesDTO fromEntityToDTO(Classes classes){
        return ClassesDTO.builder()
            .id(classes.getId() == null ? null : classes.getId())
            .name(classes.getName())
            .capacity(classes.getCapacity())
            .grade(classes.getGrade())
            .super_visor(classes.getSuper_visor())
            // .teacher_id(classes.getTeacher())
            .build();
    }
}