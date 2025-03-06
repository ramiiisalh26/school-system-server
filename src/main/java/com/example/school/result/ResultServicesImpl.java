package com.example.school.result;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResultServicesImpl implements IResultServices{

    private IResultRepositry resultRepositry;

    @Autowired
    public ResultServicesImpl(final IResultRepositry resultRepositry){
        this.resultRepositry = resultRepositry;
    }

    @Override
    public Boolean isExist(ResultDTO resultDTO) {
        return resultRepositry.existsById(resultDTO.getId());
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

        Result savedResult = resultRepositry.save(result);

        return ResultMapper.fromEntityToDTO(savedResult);
    }

    @Override
    public Optional<ResultDTO> getResultById(Long id) {
        Optional<Result> result = resultRepositry.findById(id);
        if (result.isPresent()) {
            return Optional.of(ResultMapper.fromEntityToDTO(result.get()));
        }
        return Optional.empty();
    }

    @Override
    public List<ResultDTO> getAllResults() {
        List<Result> results = resultRepositry.findAll();
        List<ResultDTO> resultsDTO = results.stream().map(result -> ResultMapper.fromEntityToDTO(result)).collect(Collectors.toList());
        return resultsDTO;
    }

    @Override
    public ResultDTO updateResult(Long id, ResultDTO resultDTO) {
        Result result = resultRepositry.findById(id).orElseThrow();
        
        if (result != null) {
            result.setClasses(resultDTO.getClasses());
            result.setDate(resultDTO.getDate());
            result.setScore(resultDTO.getScore());
            result.setSubjects(resultDTO.getSubjects());
            result.setTeacher(resultDTO.getTeacher());
            result.setType(resultDTO.getType());
            resultRepositry.save(result);
        }
        
        return ResultMapper.fromEntityToDTO(result);
    }

    @Override
    public void deleteResultById(Long id) {
        resultRepositry.deleteById(id);
    }
    
}
