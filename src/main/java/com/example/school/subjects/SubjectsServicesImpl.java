package com.example.school.subjects;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;


@Service
public class SubjectsServicesImpl implements ISubjectsServices{

    private ISubjectsRepositry IsubjectsRepositry;


    public SubjectsServicesImpl(final ISubjectsRepositry IsubjectsRepositry){
        this.IsubjectsRepositry = IsubjectsRepositry;
    }

    @Override
    public Boolean isSubjectExists(SubjectsDTO subjectsDTO) {
        return IsubjectsRepositry.existsById(subjectsDTO.getId());
    }

    @Override
    public List<SubjectsDTO> addManySubject(List<SubjectsDTO> subjectsDTOs) {
        for (SubjectsDTO subjectsDTO : subjectsDTOs) {
            addSubject(subjectsDTO);
        }
        return subjectsDTOs;
    }

    @Override
    public SubjectsDTO addSubject(SubjectsDTO subjectsDTO) {
        
        if (subjectsDTO == null) {
            throw new RuntimeException("Subject must be provided");
        }

        Subjects subjects = SubjectsMapper.fromDTOToEntity(subjectsDTO);

        Subjects savedSubjects = IsubjectsRepositry.save(subjects);

        return SubjectsMapper.fromEntityToDTO(savedSubjects);
    }

    @Override
    public Optional<SubjectsDTO> findSubjectById(Long id) {
        Optional<Subjects> subjects = IsubjectsRepositry.findById(id);
        if (subjects.isPresent()) {
            return Optional.of(SubjectsMapper.fromEntityToDTO(subjects.get()));
        }
        return Optional.empty();
    }

    @Override
    public List<SubjectsDTO> getAllSubjects() {
        List<Subjects> subjects = IsubjectsRepositry.findAll();
        List<SubjectsDTO> subjectsDTOs = subjects.stream().map(subject -> SubjectsMapper.fromEntityToDTO(subject)).collect(Collectors.toList());
        return subjectsDTOs;
    }

    @Override
    public void deleteSubject(Long id) {
        IsubjectsRepositry.deleteById(id);
    }

    @Override
    public SubjectsDTO updateSubject(Long id, SubjectsDTO subjectsDTO) {
        Subjects subject = IsubjectsRepositry.findById(id).orElseThrow();
        // List<Teacher> teachers = subject.getTeachers().stream().map(teacher -> teacher).collect(Collectors.toList());
        // Teacher teacher =  teacherRepositry.findById(teacher_id.getId()).get();
        if (subject != null) {
            subject.setName(subjectsDTO.getName());
            IsubjectsRepositry.save(subject);
        }
        return SubjectsMapper.fromEntityToDTO(subject);
    }

    @Override
    public SubjectsDTO getSubjectByName(String subjectName) {
        return SubjectsMapper.fromEntityToDTO(IsubjectsRepositry.getSubjectByName(subjectName));
    }
    
    public void deleteTeacher_subject(Long id){
        IsubjectsRepositry.deleteTeacher_subject(id);
    }
}
