package com.example.school.result;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.school.student.StudentMapper;
import com.example.school.courses.CoursesMapper;
import com.example.school.teacher.TeacherMapper;

@Service
public class ResultServicesImpl implements IResultServices {


    private final IResultRepositry IresultRepositry;

    @Autowired
    public ResultServicesImpl(final IResultRepositry IresultRepositry) {
        this.IresultRepositry = IresultRepositry;
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

        if (resultDTO == null) {
            throw new RuntimeException("Result Must e Provided");
        }

        Result result = ResultMapper.fromDTOToEntity(resultDTO);

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

        result.setStudent(StudentMapper.fromDTOToEntity(resultDTO.getStudentDTO()));
        result.setDate(resultDTO.getDate());
        result.setScore(resultDTO.getScore());
        result.setSubjects(CoursesMapper.fromDTOToEntity(resultDTO.getCoursesDTO()));
        result.setTeacher(TeacherMapper.fromDTOToEntity(resultDTO.getTeacherDTO()));
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
    public List<ResultDTO> getResultsBySubjectId(Long subjectId) {
        List<Result> results = IresultRepositry.getResultsBySubjectId(subjectId);
        return results.stream().map(ResultMapper::fromEntityToDTO).collect(Collectors.toList());
    }
}
