package com.example.school.lesson;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class LessonServicesImpl implements ILessonServices{

    private ILessonRepositry lessonRepositry;

    public LessonServicesImpl(final ILessonRepositry lessonRepositry){
        this.lessonRepositry = lessonRepositry;
    }

    @Override
    public Boolean isExist(LessonDTO lessonDTO) {
        return lessonRepositry.existsById(lessonDTO.getId());
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

        Lesson lesson = LessonMapper.fromDTOToEntity(lessonDTO);

        Lesson savedLesson = lessonRepositry.save(lesson);

        return LessonMapper.fromEntityToDTO(savedLesson);
    }

    @Override
    public Optional<LessonDTO> getLessonById(Long id) {
        Optional<Lesson> lessson = lessonRepositry.findById(id);
        if (lessson.isPresent()) {
            return Optional.of(LessonMapper.fromEntityToDTO(lessson.get()));
        }

        return Optional.empty();
    }

    @Override
    public List<LessonDTO> getAllLessons() {
        List<Lesson> lessons = lessonRepositry.findAll();
        List<LessonDTO> lessonsDTO = lessons.stream().map(lesson -> LessonMapper.fromEntityToDTO(lesson)).collect(Collectors.toList());
        return lessonsDTO;
    }

    @Override
    public LessonDTO updateLessons(Long id, LessonDTO lessonDTO) {

        Lesson lesson = lessonRepositry.findById(id).orElseThrow();

        if(lesson != null){
            lesson.setClasses(lessonDTO.getClasses());
            lesson.setSubject(lessonDTO.getSubject());
            lesson.setTeacher(lessonDTO.getTeacher());
            lessonRepositry.save(lesson);
        }
        return LessonMapper.fromEntityToDTO(lesson);
    }

    @Override
    public void deleteLessoyId(Long id) {
        lessonRepositry.deleteById(id);
    }
    
}
