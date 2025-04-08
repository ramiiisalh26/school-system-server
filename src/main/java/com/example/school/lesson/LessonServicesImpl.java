package com.example.school.lesson;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.school.classes.Classes;
import com.example.school.classes.IClassesRepositry;
import com.example.school.courses.Courses;
import com.example.school.courses.ICoursesRepositry;
import com.example.school.teacher.ITeacherRepositry;
import com.example.school.teacher.Teacher;
import org.springframework.stereotype.Service;

@Service
public class LessonServicesImpl implements ILessonServices{

    private final ILessonRepositry IlessonRepositry;
    private final ICoursesRepositry IcoursesRepositry;
    private final IClassesRepositry IclassesRepositry;
    private final ITeacherRepositry IteacherRepositry;

    public LessonServicesImpl(
            final ILessonRepositry IlessonRepositry,
            final ICoursesRepositry IcoursesRepositry,
            final IClassesRepositry IclassesRepositry,
            final ITeacherRepositry IteacherRepositry)
    {
        this.IlessonRepositry = IlessonRepositry;
        this.IclassesRepositry = IclassesRepositry;
        this.IteacherRepositry = IteacherRepositry;
        this.IcoursesRepositry = IcoursesRepositry;
    }

    @Override
    public Boolean isExist(LessonDTO lessonDTO) {
        return IlessonRepositry.existsById(lessonDTO.getId());
    }

    @Override
    public List<LessonDTO> addManyLessons(List<LessonDTO> lessonsDTO) {
        for (LessonDTO lessonDTO : lessonsDTO) {
            addLesson(lessonDTO);
        }
        return lessonsDTO;
    }

    @Override
    public LessonDTO addLesson(LessonDTO lessonDTO) {
        if (lessonDTO == null) {
            throw new RuntimeException("Lessons Must e provided");
        }

        Courses courses = IcoursesRepositry.getCourseByCourse_code(lessonDTO.getCourse_code());
        if (courses == null) throw new RuntimeException("Course Must be provided");

        Classes classes = IclassesRepositry.getClassesByName(lessonDTO.getClass_name());
        if (classes == null) throw new RuntimeException("Classes Must be provided");

        Teacher teacher = IteacherRepositry.getTeacherByTeacher_id(lessonDTO.getTeacher_code());
        if (teacher == null) throw new RuntimeException("Teacher Must be provided");

        Lesson lesson = Lesson.builder()
                .courses(courses)
                .classes(classes)
                .teacher(teacher)
                .startDate(lessonDTO.getStart_date())
                .build();

        Lesson savedLesson = IlessonRepositry.save(lesson);

        return LessonMapper.fromEntityToDTO(savedLesson);
    }

    @Override
    public Optional<LessonDTO> getLessonById(Long id) {
        Optional<Lesson> lesson = IlessonRepositry.findById(id);
        return lesson.map(LessonMapper::fromEntityToDTO);

    }

    @Override
    public List<LessonDTO> getAllLessons() {
        List<Lesson> lessons = IlessonRepositry.findAll();
        return lessons.stream().map(LessonMapper::fromEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public LessonDTO updateLessons(Long id, LessonDTO lessonDTO) {

        Lesson lesson = IlessonRepositry.findById(id).orElseThrow();

        Courses courses = IcoursesRepositry.getCourseByCourse_code(lessonDTO.getCourse_code());
        if (courses == null) throw new RuntimeException("Course Must be provided");

        Classes classes = IclassesRepositry.getClassesByName(lessonDTO.getClass_name());
        if (classes == null) throw new RuntimeException("Classes Must be provided");

        Teacher teacher = IteacherRepositry.getTeacherByTeacher_id(lessonDTO.getTeacher_code());
        if (teacher == null) throw new RuntimeException("Teacher Must be provided");

        lesson.setClasses(classes);
        lesson.setCourses(courses);
        lesson.setTeacher(teacher);
        lesson.setStartDate(lessonDTO.getStart_date());
        IlessonRepositry.save(lesson);

        return LessonMapper.fromEntityToDTO(lesson);
    }

    @Override
    public void deleteLessonById(Long id) {
        IlessonRepositry.deleteById(id);
    }

    @Override
    public List<LessonDTO> getLessonByCourseCode(String course_code) {
        List<Lesson> lessons = IlessonRepositry.getLessonByCourseCode(course_code);
        return lessons.stream().map(LessonMapper::fromEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public List<LessonDTO> getLessonByClassName(String class_name) {
        List<Lesson> lessons = IlessonRepositry.getLessonByClassName(class_name);
        return lessons.stream().map(LessonMapper::fromEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public List<LessonDTO> getLessonByTeacherCode(String teacher_code) {
        List<Lesson> lessons = IlessonRepositry.getLessonByTeacherCode(teacher_code);
        return lessons.stream().map(LessonMapper::fromEntityToDTO).collect(Collectors.toList());
    }

}
