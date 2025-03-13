package com.example.school.result;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IResultRepositry extends JpaRepository<Result,Long>{

    @Query("SELECT r FROM Result r JOIN r.teacher WHERE r.id = :id ")
    List<Result> getResultsByTeacherId(Long id);

    @Query("SELECT r FROM Result r JOIN r.student WHERE r.id = :id")
    List<Result> getResultsByStudentId(Long id);

    @Query("SELECT r FROM Result r JOIN r.subjects WHERE r.id = :id")
    List<Result> getResultsBySubjectId(Long id);
}
