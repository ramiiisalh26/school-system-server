package com.example.school.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClassesServicesImpl implements IClassesServices{

    private final IClassesRepositry IclassesRepositry;

    @Autowired
    public ClassesServicesImpl(IClassesRepositry IclassesRepositry){
        this.IclassesRepositry = IclassesRepositry;
    }

    @Override
    public Boolean isClassExists(ClassesDTO classesDTO) {
        return IclassesRepositry.existsById(classesDTO.getId());
    }

    @Override
    public List<ClassesDTO> createManyClass(List<ClassesDTO> classesDTOs){
        for (ClassesDTO classesDTO : classesDTOs) {
            createClass(classesDTO);
        }
        return classesDTOs;
    }

    @Override
    public ClassesDTO createClass(ClassesDTO classesDTO) {
        
        if (classesDTO == null) {throw new RuntimeException("Class Must be provided"); }

        Classes classes = ClassesMapper.fromDTOToEntity(classesDTO);

        Classes savedClasses = IclassesRepositry.save(classes);

        return ClassesMapper.fromEntityToDTO(savedClasses);
    }

    @Override
    public Optional<ClassesDTO> findById(Long id) {
        Optional<Classes> foundedClass = IclassesRepositry.findById(id);

        return foundedClass.map(ClassesMapper::fromEntityToDTO);
    }

    @Override
    public List<ClassesDTO> getAllClasses() {
        List<Classes> classes = IclassesRepositry.findAll();
        return classes.stream().map(ClassesMapper::fromEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteClassesById(Long id) {
        IclassesRepositry.deleteById(id);
    }

    @Override
    public ClassesDTO updateClass(Long id, ClassesDTO classesDTO) {
        
        Classes classes = IclassesRepositry.findById(id).orElseThrow();

        classes.setName(classesDTO.getName());
        classes.setGrade(classesDTO.getGrade());
        classes.setCapacity(classesDTO.getCapacity());
        classes.setSuper_visor(classesDTO.getSuper_visor());
        IclassesRepositry.save(classes);

        return ClassesMapper.fromEntityToDTO(classes);
    }

    @Override
    public ClassesDTO getClassesByName(String name) {
        return ClassesMapper.fromEntityToDTO(IclassesRepositry.getClassesByName(name));
    }

    
    @Override
    public List<ClassesDTO> getClassesByTeacherId(Long tracher_id) {
        System.out.println("hello");
        List<Classes> classes = IclassesRepositry.getClassesByTeacher_id(tracher_id);
        // System.out.println(classes.get(0));
        List<ClassesDTO> classesDTOs = new ArrayList<ClassesDTO>();
        classes.forEach(cla -> {
            classesDTOs.add(ClassesMapper.fromEntityToDTO(cla));
        });
        return classesDTOs;
    }

    @Override
    public List<ClassesDTO> getClassesByTeacher_code(String teacher_code) {
        return IclassesRepositry.getClassesByTeacher_code(teacher_code).stream().map(ClassesMapper::fromEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public List<ClassesDTO> getClassesByCourses_code(String course_code) {
        return IclassesRepositry.getClassesByCourses_code(course_code).stream().map(ClassesMapper::fromEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public List<ClassesDTO> getClassesByStudent_id(Long student_id) {
        return IclassesRepositry.getClassesByStudent_id(student_id).stream().map(ClassesMapper::fromEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public List<ClassesDTO> getClassesByStudent_code(String student_code) {
        return IclassesRepositry.getClassesByStudent_code(student_code).stream().map(ClassesMapper::fromEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public List<ClassesDTO> getClassesByResults_id(Long result_id) {
        return IclassesRepositry.getClassesByResults_id(result_id).stream().map(ClassesMapper::fromEntityToDTO).collect(Collectors.toList());
    }
}
