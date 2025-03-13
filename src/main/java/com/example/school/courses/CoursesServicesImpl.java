package com.example.school.courses;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;


@Service
public class CoursesServicesImpl implements ICoursesServices {

    private final ICoursesRepositry IcoursesRepositry;

    public CoursesServicesImpl(final ICoursesRepositry IcoursesRepositry){
        this.IcoursesRepositry = IcoursesRepositry;
    }

    @Override
    public Boolean isCoursesExists(CoursesDTO coursesDTO) {
        return IcoursesRepositry.existsById(coursesDTO.getId());
    }

    @Override
    public List<CoursesDTO> addManyCourses(List<CoursesDTO> coursesDTOS) {
        for (CoursesDTO coursesDTO : coursesDTOS) {
            addCourse(coursesDTO);
        }
        return coursesDTOS;
    }

    @Override
    public CoursesDTO addCourse(CoursesDTO coursesDTO) {
        
        if (coursesDTO == null) {
            throw new RuntimeException("Subject must be provided");
        }

        Courses courses = CoursesMapper.fromDTOToEntity(coursesDTO);

        Courses savedCourses = IcoursesRepositry.save(courses);

        return CoursesMapper.fromEntityToDTO(savedCourses);
    }

    @Override
    public Optional<CoursesDTO> findCourseById(Long id) {
        Optional<Courses> subjects = IcoursesRepositry.findById(id);
        return subjects.map(CoursesMapper::fromEntityToDTO);
    }

    @Override
    public List<CoursesDTO> getAllCourses() {
        List<Courses> courses = IcoursesRepositry.findAll();
        return courses.stream().map(CoursesMapper::fromEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteCourses(Long id) {
        IcoursesRepositry.deleteById(id);
    }

    @Override
    public CoursesDTO updateCourses(Long id, CoursesDTO coursesDTO) {
        Courses courses = IcoursesRepositry.findById(id).orElseThrow();


        courses.setName(coursesDTO.getName());
        IcoursesRepositry.save(courses);

        return CoursesMapper.fromEntityToDTO(courses);
    }

    @Override
    public CoursesDTO getCoursesByName(String subjectName) {
        return CoursesMapper.fromEntityToDTO(IcoursesRepositry.getSubjectByName(subjectName));
    }
    
    @Override
    public List<CoursesDTO> getAllSujectsByTeacher_id(Long id){
        List<Courses> subjects = IcoursesRepositry.getAllSujectsByTeacher_id(id);
        return subjects.stream().map(CoursesMapper::fromEntityToDTO).collect(Collectors.toList());
    }

    public void deleteTeacher_subject(Long id){
        IcoursesRepositry.deleteTeacher_subject(id);
    }
}
