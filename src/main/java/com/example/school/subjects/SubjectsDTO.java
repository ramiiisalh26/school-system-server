package com.example.school.subjects;

import java.util.ArrayList;
import java.util.List;

import com.example.school.teacher.TeacherDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubjectsDTO {
    Long id;
    String name;
    List<TeacherDTO> teachers;
}
