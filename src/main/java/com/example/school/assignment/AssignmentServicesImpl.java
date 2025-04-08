package com.example.school.assignment;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.school.classes.Classes;
import com.example.school.classes.IClassesRepositry;
import com.example.school.courses.Courses;
import com.example.school.courses.ICoursesRepositry;
import com.example.school.student.IStudentRepositry;
import com.example.school.student.Student;
import com.example.school.teacher.ITeacherRepositry;
import com.example.school.teacher.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssignmentServicesImpl implements IAssignmentServices{

    private final IAssignmentRepository IassignmentRepository;
    private final IStudentRepositry IstudentRepositry;
    private final ICoursesRepositry IcoursesRepositry;
    private final ITeacherRepositry IteacherRepositry;
    private final IClassesRepositry IclassesRepositry;
    @Autowired
    public AssignmentServicesImpl(
            final IAssignmentRepository IassignmentRepository,
            final IStudentRepositry IstudentRepositry,
            final ICoursesRepositry IcoursesRepositry,
            final ITeacherRepositry IteacherRepositry,
            final IClassesRepositry IclassesRepositry
    ){

        this.IassignmentRepository = IassignmentRepository;
        this.IstudentRepositry = IstudentRepositry;
        this.IcoursesRepositry = IcoursesRepositry;
        this.IteacherRepositry = IteacherRepositry;
        this.IclassesRepositry = IclassesRepositry;
    }

    @Override
    public Boolean isExist(AssignmentDTO assignmentDTO) {
        return IassignmentRepository.existsById(assignmentDTO.getId());
    }

    @Override
    public List<AssignmentDTO> getAllAssignment() {
        List<Assignment> assignments = IassignmentRepository.findAll();
        return assignments.stream().map(AssignmentMapper::fromEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public List<AssignmentDTO> addManyAssignment(List<AssignmentDTO> assignmentsDTO) {
        for (AssignmentDTO assignmentDTO : assignmentsDTO) {
            addAssignment(assignmentDTO);
        }
        return assignmentsDTO;
    }

    @Override
    public void addAssignment(AssignmentDTO assignmentDTO) {
        if (assignmentDTO == null) {
            throw new RuntimeException("Assignment must be Provided");
        }

        Student student = IstudentRepositry.getByStudentCode(assignmentDTO.getStudent_code());
        if (student == null) {
            throw new RuntimeException("Student with this code does not exist");
        }

        Teacher teacher = IteacherRepositry.getTeacherByTeacher_id(assignmentDTO.getTeacher_code());
        if (teacher == null) {
            throw new RuntimeException("Teacher with this code does not exist");
        }

        Courses courses = IcoursesRepositry.getCourseByCourse_code(assignmentDTO.getCourse_code());
        if (courses == null) {
            throw new RuntimeException("Course with this code does not exist");
        }

        Classes classes = IclassesRepositry.getClassesByName(assignmentDTO.getClass_name());
        if (classes == null) {
            throw new RuntimeException("Class with this name does not exist");
        }

        Assignment assignment = Assignment.builder()
                .student(student)
                .teacher(teacher)
                .courses(courses)
                .classes(classes)
                .dueDate(new Date())
                .build();

        IassignmentRepository.save(assignment);
    }

    @Override
    public Optional<AssignmentDTO> getAssignmentById(Long id) {
        Optional<Assignment> assignment = IassignmentRepository.findById(id);

        return assignment.map(AssignmentMapper::fromEntityToDTO);

    }

    @Override
    public AssignmentDTO updateAssignment(Long id, AssignmentDTO assignmentDTO) {
        Assignment assignment = IassignmentRepository.findById(id).orElseThrow();

//            assignment.setClasses(assignmentDTO.getClasses());
//            assignment.setCourses(assignmentDTO.getCourses());
//            assignment.setTeacher(assignmentDTO.getTeacher());
//            assignment.setDueDate(assignmentDTO.getDueDate());
//            assignmentRespositry.save(assignment);

        return AssignmentMapper.fromEntityToDTO(assignment);
    }

    @Override
    public void deleteAssignmentById(Long id) {
        IassignmentRepository.deleteById(id);
    }

    @Override
    public List<AssignmentDTO> getAssignmentsByTeacherCode(String teacher_code) {
        List<Assignment> assignments = IassignmentRepository.getAssignmentsByTeacherCode(teacher_code);
        return assignments.stream().map(AssignmentMapper::fromEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public List<AssignmentDTO> getAssignmentsByCoursesCode(String courses_code) {
        List<Assignment> assignments = IassignmentRepository.getAssignmentsByCoursesCode(courses_code);
        return assignments.stream().map(AssignmentMapper::fromEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public List<AssignmentDTO> getAssignmentsByStudentCode(String student_code) {
        List<Assignment> assignments = IassignmentRepository.getAssignmentsByStudentCode(student_code);
        return assignments.stream().map(AssignmentMapper::fromEntityToDTO).collect(Collectors.toList());
    }

}
