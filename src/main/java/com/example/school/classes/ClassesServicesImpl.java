package com.example.school.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClassesServicesImpl implements IClassesServices{

    private IClassesRepositry classesRepositry;

    @Autowired
    public ClassesServicesImpl(IClassesRepositry classesRepositry){
        this.classesRepositry = classesRepositry;
    }

    @Override
    public Boolean isClassExists(ClassesDTO classesDTO) {
        return classesRepositry.existsById(classesDTO.getId());
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

        Classes savedClasses = classesRepositry.save(classes);

        return ClassesMapper.fromEntityToDTO(savedClasses);
    }

    @Override
    public Optional<ClassesDTO> findById(Long id) {
        Optional<Classes> foundedClass = classesRepositry.findById(id);

        if (foundedClass.isPresent()) {
            return Optional.of(ClassesMapper.fromEntityToDTO(foundedClass.get()));
        }

        return Optional.empty();

    }

    @Override
    public List<ClassesDTO> getAllClasses() {
        List<Classes> classes = classesRepositry.findAll();
        List<ClassesDTO> classesDTOs = classes.stream().map(classe -> ClassesMapper.fromEntityToDTO(classe)).collect(Collectors.toList());
        return classesDTOs;
    }

    @Override
    public void deleteClassbyId(Long id) {
        classesRepositry.deleteById(id);
    }

    @Override
    public ClassesDTO updateClass(Long id, ClassesDTO classesDTO) {
        
        Classes classes = classesRepositry.findById(id).orElseThrow();

        if (classes != null) {
            classes.setName(classesDTO.getName());
            classes.setGrade(classesDTO.getGrade());
            classes.setCapacity(classesDTO.getCapacity());
            classes.setSuper_visor(classesDTO.getSuper_visor());
            classesRepositry.save(classes);
        }        
        return ClassesMapper.fromEntityToDTO(classes);
    }

    @Override
    public ClassesDTO getClassesByName(String name) {
        return ClassesMapper.fromEntityToDTO(classesRepositry.getClassesByName(name));
    }

    
    @Override
    public List<ClassesDTO> getClassesByTeacherId(Long tracher_id) {
        System.out.println("hello");
        List<Classes> classes = classesRepositry.getClassesByTeacher_id(tracher_id);
        // System.out.println(classes.get(0));
        List<ClassesDTO> classesDTOs = new ArrayList<ClassesDTO>();
        classes.forEach(cla -> {
            classesDTOs.add(ClassesMapper.fromEntityToDTO(cla));
        });
        // System.out.println(classesDTOs);
        return classesDTOs;
    }
    
    @Override 
    public void setTeacherIdToBeNull(Long id){
        classesRepositry.setTeacherIdToBeNull(id);
    }
}
