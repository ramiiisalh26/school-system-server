package com.example.school.subjects;

import java.util.List;
import java.util.Optional;

public interface ISubjectsServices {
    
    Boolean isSubjectExists(SubjectsDTO subjectsDTO);

    List<SubjectsDTO> addManySubject(List<SubjectsDTO> subjectsDTOs);

    SubjectsDTO addSubject(SubjectsDTO subjectsDTO);

    Optional<SubjectsDTO> findSubjectById(Long id);

    List<SubjectsDTO> getAllSubjects();

    void deleteSubject(Long id);

    SubjectsDTO updateSubject(Long id, SubjectsDTO subjectsDTO);

    SubjectsDTO getSubjectByName(String subjectName);

    void deleteTeacher_subject(Long id);
}
