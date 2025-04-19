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
            throw new RuntimeException("course must be provided");
        }

        Courses courses = CoursesMapper.fromDTOToEntity(coursesDTO);

        String courseCode = genereateCourseCode(coursesDTO.getDepartment());

        courses.setCourse_code(courseCode);

        Courses savedCourses = IcoursesRepositry.save(courses);

        return CoursesMapper.fromEntityToDTO(savedCourses);
    }

    @Override
    public Optional<CoursesDTO> findCourseById(Long id) {
        Optional<Courses> courses = IcoursesRepositry.findById(id);
        return courses.map(CoursesMapper::fromEntityToDTO);
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
    public CoursesDTO getCoursesByName(String courseName) {
        return CoursesMapper.fromEntityToDTO(IcoursesRepositry.getCourseByName(courseName));
    }

    @Override
    public List<CoursesDTO> getAllCoursesByDepartment(String department) {
        return IcoursesRepositry.getAllCoursesByDepartment(department).stream().map(CoursesMapper::fromEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public List<CoursesDTO> getAllCoursesByTeacher_id(Long id){
        List<Courses> courses = IcoursesRepositry.getAllCoursesByTeacher_id(id);
        return courses.stream().map(CoursesMapper::fromEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public List<CoursesDTO> getAllCoursesByTeacherCode(String teacher_code) {
        return IcoursesRepositry.getAllCoursesByTeacherCode(teacher_code).stream().map(CoursesMapper::fromEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public List<CoursesDTO> getAllCoursesByStudentId(Long student_id) {
        return IcoursesRepositry.getAllCoursesByStudentId(student_id).stream().map(CoursesMapper::fromEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public List<CoursesDTO> getAllCoursesByStudentCode(String student_code) {
        return IcoursesRepositry.getAllCoursesByStudentCode(student_code).stream().map(CoursesMapper::fromEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public List<CoursesDTO> getAllCoursesByResultsId(Long result_id) {
        return IcoursesRepositry.getAllCoursesByResultsId(result_id).stream().map(CoursesMapper::fromEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public List<CoursesDTO> getAllCoursesByAssignmentId(Long assignment_id) {
        return IcoursesRepositry.getAllCoursesByAssignmentId(assignment_id).stream().map(CoursesMapper::fromEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public List<CoursesDTO> getAllCoursesByClassesId(Long class_id) {
        return IcoursesRepositry.getAllCoursesByClassesId(class_id).stream().map(CoursesMapper::fromEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public List<CoursesDTO> getAllCoursesByClassesName(String class_name) {
        return IcoursesRepositry.getAllCoursesByClassesName(class_name).stream().map(CoursesMapper::fromEntityToDTO).collect(Collectors.toList());
    }

    private String genereateCourseCode(String prefix){

        String lastCourseCode = IcoursesRepositry.FindLastCourseCode(prefix + "%");

        int newNumber = 1;

        if (lastCourseCode != null) {
            String numberPart = lastCourseCode.replaceAll("[^0-9]", "");
            newNumber = Integer.parseInt(numberPart) + 1;
        }
        return prefix + newNumber;
    }

}
