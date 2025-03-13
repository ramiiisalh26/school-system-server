package com.example.school.parent;

import java.util.ArrayList;
import java.util.stream.Collectors;

import com.example.school.address.AddressMapper;
import com.example.school.student.StudentMapper;

public class ParentMapper {
    
    public static Parent fromDTOToEntity(ParentDTO parentDTO){
        return Parent.builder()
                .id(parentDTO.getId())
                .first_name(parentDTO.getFirst_name())
                .middle_name(parentDTO.getMiddle_name())
                .last_name(parentDTO.getLast_name())
                .phone(parentDTO.getPhone())
                .email(parentDTO.getEmail())
                .address(AddressMapper.fromDTOToEntity(parentDTO.getAddress()))
                .student(parentDTO.getStudents() == null ? new ArrayList<>() : parentDTO.getStudents().stream().map(StudentMapper::fromDTOToEntity).collect(Collectors.toList()))
                .build();
    }

    public static ParentDTO fromEntityToDTO(Parent parent){
        return ParentDTO.builder()
                .id(parent.getId())
                .first_name(parent.getFirst_name())
                .middle_name(parent.getMiddle_name())
                .last_name(parent.getLast_name())
                .phone(parent.getPhone())
                .email(parent.getEmail())
                .address(AddressMapper.fromEntityToDTO(parent.getAddress()))
                .students(parent.getStudent() == null ? new ArrayList<>() : parent.getStudent().stream().map(StudentMapper::fromEntityToDTO).collect(Collectors.toList()))
                .build();
    }
}
