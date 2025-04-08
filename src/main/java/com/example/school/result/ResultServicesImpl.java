package com.example.school.result;

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
public class ResultServicesImpl implements IResultServices {


    private final IResultRepositry IresultRepositry;
    private final IStudentRepositry IstudentRepositry;
    private final ICoursesRepositry IcoursesRepositry;
    private final ITeacherRepositry IteacherRepositry;
    private final IClassesRepositry IclassesRepositry;

    @Autowired
    public ResultServicesImpl(
            final IResultRepositry IresultRepositry,
            final IStudentRepositry IstudentRepositry,
            final ICoursesRepositry IcoursesRepositry,
            final ITeacherRepositry IteacherRepositry,
            final IClassesRepositry IclassesRepositry
    ) {
        this.IresultRepositry = IresultRepositry;
        this.IstudentRepositry = IstudentRepositry;
        this.IcoursesRepositry = IcoursesRepositry;
        this.IteacherRepositry = IteacherRepositry;
        this.IclassesRepositry = IclassesRepositry;
    }

    @Override
    public Boolean isExist(ResultDTO resultDTO) {
        return IresultRepositry.existsById(resultDTO.getId());
    }

    @Override
    public List<ResultDTO> addManyResult(List<ResultDTO> resultsDTO) {
        for (ResultDTO resultDTO : resultsDTO) {
            addResult(resultDTO);
        }
        return resultsDTO;
    }

    @Override
    public ResultDTO addResult(ResultDTO resultDTO) {

        if (resultDTO == null) throw new RuntimeException("Result Must e Provided");

        Student student = IstudentRepositry.getByStudentCode(resultDTO.getStudent_code());
        if (student == null) throw new RuntimeException("Student Must be provided");

        Courses courses = IcoursesRepositry.getCourseByCourse_code(resultDTO.getCourse_code());
        if (courses == null) throw new RuntimeException("Course Must be provided");

        Classes classes = IclassesRepositry.getClassesByName(resultDTO.class_name);
        if (classes == null) throw new RuntimeException("Classes Must be provided");

        Teacher teacher = IteacherRepositry.getTeacherByTeacher_id(resultDTO.getTeacher_id());
        if (teacher == null) throw new RuntimeException("Teacher Must be provided");

        Result result = Result.builder()
                .student(student)
                .courses(courses)
                .classes(classes)
                .teacher(teacher)
                .score(resultDTO.getScore())
                .type(resultDTO.getType())
                .date(new Date())
                .build();

        Result savedResult = IresultRepositry.save(result);

        return ResultMapper.fromEntityToDTO(savedResult);
    }

    @Override
    public Optional<ResultDTO> getResultById(Long id) {
        Optional<Result> result = IresultRepositry.findById(id);
        return result.map(ResultMapper::fromEntityToDTO);
    }

    @Override
    public List<ResultDTO> getAllResults() {
        List<Result> results = IresultRepositry.findAll();
        return results.stream().map(ResultMapper::fromEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public ResultDTO updateResult(Long id, ResultDTO resultDTO) {

        Result result = IresultRepositry.findById(id).orElseThrow();

        Student student = IstudentRepositry.getByStudentCode(resultDTO.getStudent_code());
        if (student == null) throw new RuntimeException("Student Must be provided");

        Teacher teacher = IteacherRepositry.getTeacherByTeacher_id(resultDTO.getTeacher_id());
        if (teacher == null) throw new RuntimeException("Teacher Must be provided");

        Classes classes = IclassesRepositry.getClassesByName(resultDTO.class_name);
        if (classes == null) throw new RuntimeException("Classes Must be provided");

        Courses courses = IcoursesRepositry.getCourseByCourse_code(resultDTO.getCourse_code());
        if (courses == null) throw new RuntimeException("Course Must be provided");

        result.setStudent(student);
        result.setCourses(courses);
        result.setClasses(classes);
        result.setTeacher(teacher);
        result.setDate(new Date());
        result.setScore(resultDTO.getScore());
        result.setType(resultDTO.getType());
        IresultRepositry.save(result);

        return ResultMapper.fromEntityToDTO(result);
    }

    @Override
    public void deleteResultById(Long id) {
        IresultRepositry.deleteById(id);
    }

    @Override
    public List<ResultDTO> getResultsByTeacherId(Long teacherId) {
        List<Result> results = IresultRepositry.getResultsByTeacherId(teacherId);
        return results.stream().map(ResultMapper::fromEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public List<ResultDTO> getResultsByStudentId(Long studentId) {
        List<Result> results = IresultRepositry.getResultsByStudentId(studentId);
        return results.stream().map(ResultMapper::fromEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public List<ResultDTO> getResultsByCourseId(Long courseId) {
        List<Result> results = IresultRepositry.getResultsByCourseId(courseId);
        return results.stream().map(ResultMapper::fromEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public List<ResultDTO> getResultsByStudentCode(String studentCode){
        List<Result> results = IresultRepositry.getResultsByStudentCode(studentCode);
        return  results.stream().map(ResultMapper::fromEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public List<ResultDTO> getResultByCourseCode(String courseCode){
        List<Result> results = IresultRepositry.getResultByCourseCode(courseCode);
        return  results.stream().map(ResultMapper::fromEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public List<ResultDTO> getResultByTeacherCode(String teacherCode){
        List<Result> results = IresultRepositry.getResultByTeacherCode(teacherCode);
        return  results.stream().map(ResultMapper::fromEntityToDTO).collect(Collectors.toList());
    }

}
