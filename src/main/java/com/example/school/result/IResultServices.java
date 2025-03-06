package com.example.school.result;

import java.util.List;
import java.util.Optional;

public interface IResultServices {

    Boolean isExist(ResultDTO resultDTO);

    List<ResultDTO> addManyResult(List<ResultDTO> resultsDTO);

    ResultDTO addResult(ResultDTO resultDTO);

    Optional<ResultDTO> getResultById(Long id);

    List<ResultDTO> getAllResults();

    ResultDTO updateResult(Long id,ResultDTO resultDTO);

    void deleteResultById(Long id);
}
