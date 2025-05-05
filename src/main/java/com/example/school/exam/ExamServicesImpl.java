package com.example.school.exam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.school.classes.Classes;
import com.example.school.classes.IClassesRepositry;
import com.example.school.courses.Courses;
import com.example.school.courses.ICoursesRepositry;
import com.example.school.teacher.ITeacherRepositry;
import com.example.school.teacher.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExamServicesImpl implements IExamServices{

    private final IExamRepositry IexamRepositry;
    private final ITeacherRepositry IteacherRepositry;
    private final ICoursesRepositry IcoursesRepositry;
    private final IClassesRepositry IclassesRepositry;

    @Autowired
    public ExamServicesImpl(
            final IExamRepositry IexamRepositry,
            final ITeacherRepositry IteacherRepositry,
            final ICoursesRepositry IcoursesRepositry,
            final IClassesRepositry IclassesRepositry
            ){
        this.IexamRepositry = IexamRepositry;
        this.IteacherRepositry = IteacherRepositry;
        this.IcoursesRepositry = IcoursesRepositry;
        this.IclassesRepositry = IclassesRepositry;
    }

    @Override
    public Boolean isExist(ExamDTO examDTO) {
        return IexamRepositry.existsById(examDTO.getId());
    }

    @Override
    public List<ExamDTO> addManyExams(List<ExamDTO> examsDTO) {
        for (ExamDTO examDTO : examsDTO) {
            addExam(examDTO);
        }
        return examsDTO;
    }

    @Override
    public ExamDTO addExam(ExamDTO examDTO) {
        if (examDTO == null) {
            throw new RuntimeException("Exam Must be provided");
        }

        Courses courses = IcoursesRepositry.getCourseByCourse_code(examDTO.getCourse_code());
        if (courses == null) throw new RuntimeException("Course Must be provided");

        Classes classes = IclassesRepositry.getClassesByName(examDTO.getClass_name());
        if (classes == null) throw new RuntimeException("Classes Must be provided");

        Teacher teacher = IteacherRepositry.getTeacherByTeacher_id(examDTO.getTeacher_code());
        if (teacher == null) throw new RuntimeException("Teacher Must be provided");

        Exam exam = Exam.builder()
                .courses(courses)
                .classes(classes)
                .teacher(teacher)
                .date(examDTO.getDate())
                .build();

        Exam savedExam = IexamRepositry.save(exam);

        return ExamMapper.fromEntityToDTO(savedExam);
    }

    @Override
    public Optional<ExamDTO> getExamById(Long id) {
        Optional<Exam> exam = IexamRepositry.findById(id);
        return exam.map(ExamMapper::fromEntityToDTO);
    }

    @Override
    public List<ExamDTO> getAllExams() {
        List<Exam> exams = IexamRepositry.findAll();
        return exams.stream().map(ExamMapper::fromEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public ExamDTO updateExam(Long id,ExamDTO examDTO) {
        Exam exam = IexamRepositry.findById(id).orElseThrow();

        Courses courses = IcoursesRepositry.getCourseByCourse_code(examDTO.getCourse_code());
        if (courses == null) throw new RuntimeException("Course Must be provided");
        Classes classes = IclassesRepositry.getClassesByName(examDTO.getClass_name());
        if (classes == null) throw new RuntimeException("Classes Must be provided");
        Teacher teacher = IteacherRepositry.getTeacherByTeacher_id(examDTO.getTeacher_code());
        if (teacher == null) throw new RuntimeException("Teacher Must be provided");

        exam.setCourses(courses);
        exam.setClasses(classes);
        exam.setTeacher(teacher);
        exam.setDate(examDTO.getDate());

        Exam savedExam = IexamRepositry.save(exam);

        return ExamMapper.fromEntityToDTO(savedExam);
    }

    @Override
    public void deleteExam(Long id) {
        IexamRepositry.deleteById(id);
    }

    @Override
    public List<ExamDTO> getExamsByClassName(String class_name) {
        List<Exam> exams = IexamRepositry.getExamByClassName(class_name);
        return exams.stream().map(ExamMapper::fromEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public List<ExamDTO> getExamsByTeacherCode(String teacher_code) {
        List<Exam> exams = IexamRepositry.getExamByTeacherCode(teacher_code);
        return exams.stream().map(ExamMapper::fromEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public List<ExamDTO> getExamsByCourseCode(String course_code) {
        List<Exam> exams = IexamRepositry.getExamByCourseCode(course_code);
        return exams.stream().map(ExamMapper::fromEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public List<ExamDTO> getExamByCourseName(String course_name) {
        return IexamRepositry.getExamByCourseName(course_name).stream().map(ExamMapper::fromEntityToDTO).collect(Collectors.toList());
    }
}
