package com.example.school.attendance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IAttendanceRepositry extends JpaRepository<Attendance, Long> {

    @Query("SELECT a FROM Attendance a WHERE a.id = :id")
    Attendance getAttendanceById(Long id);

    @Query("SELECT a FROM Attendance a JOIN a.student WHERE a.student.student_id = :student_code")
    List<Attendance> getAttendanceByStudentCode(String student_code);

    @Query("SELECT a FROM Attendance a JOIN a.courses WHERE a.courses.course_code = :courses_code")
    List<Attendance> getAttendancesByCoursesCode(String courses_code);

    @Query(value = "SELECT * FROM _attendance a WHERE CONVERT(date, a.date) = :date",nativeQuery = true)
    List<Attendance> getAttendancesByDate(LocalDate date);

    @Query(value = "SELECT * FROM _attendance a WHERE CONVERT(DATE,a.date) BETWEEN :from AND :to AND a.status = :status",nativeQuery = true)
    List<Attendance> getAttendancesFromToday(LocalDate from,LocalDate to, String status);


}
