package com.example.school.result;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IResultRepositry extends JpaRepository<Result,Long>{

    @Query("SELECT r FROM Result r JOIN r.teacher t WHERE t.id = :id")
    List<Result> getResultsByTeacherId(Long id);

    @Query("SELECT r FROM Result r JOIN r.teacher t WHERE t.teacher_id = :teacherCode")
    List<Result> getResultByTeacherCode(String teacherCode);

    @Query("SELECT r FROM Result r JOIN r.student s WHERE s.id = :id")
    List<Result> getResultsByStudentId(Long id);

    @Query("SELECT r FROM Result r JOIN r.student s WHERE s.student_id = :studentCode")
    List<Result> getResultsByStudentCode(String studentCode);

    @Query("SELECT r FROM Result r JOIN r.courses c WHERE c.id = :id")
    List<Result> getResultsByCourseId(Long id);

    @Query("SELECT r FROM Result r JOIN r.courses c WHERE c.course_code = :courseCode")
    List<Result> getResultByCourseCode(String courseCode);


}
