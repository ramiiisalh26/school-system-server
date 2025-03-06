package com.example.school.parent;

import java.util.ArrayList;
import java.util.stream.Collectors;

import com.example.school.student.StudentMapper;

public class ParentMapper {
    
    public static Parent fromDTOToEntity(ParentDTO parentDTO){
        return Parent.builder()
            .id(parentDTO.getId())
            .name(parentDTO.getName())
            .phone(parentDTO.getPhone())
            .email(parentDTO.getEmail())
            .address(parentDTO.getAddress())
            .student(parentDTO.getStudent() == null ? new ArrayList<>() : parentDTO.getStudent().stream().map(StudentMapper::fromDTOToEntity).collect(Collectors.toList()))
            .build();
    }

    public static ParentDTO fromEntityToDTO(Parent parent){
        return ParentDTO.builder()
            .id(parent.getId())
            .name(parent.getName())
            .phone(parent.getPhone())
            .email(parent.getEmail())
            .address(parent.getAddress())
            .student(parent.getStudent() == null ? new ArrayList<>() : parent.getStudent().stream().map(StudentMapper::fromEntityToDTO).collect(Collectors.toList()))
            .build();
    }
}
