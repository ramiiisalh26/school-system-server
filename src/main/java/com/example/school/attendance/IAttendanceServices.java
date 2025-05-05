package com.example.school.attendance;

import java.util.List;

public interface IAttendanceServices {

    Boolean isExist(AttendanceDTO attendance);

    List<AttendanceDTO> addManyAttendance(List<AttendanceDTO> attendance);

    void addAttendance(AttendanceDTO attendance);

    AttendanceDTO getAttendanceById(Long id);

    List<AttendanceDTO> getAllAttendance();

    void updateAttendance(Long id, AttendanceDTO attendance);

    List<AttendanceDTO> getAllAttendanceByCourse(String course_code);

    List<AttendanceDTO> getAllAttendanceByStudent(String student_code);

    List<AttendanceDTO> getAttendancesFromToday(String from, String to, String status);

    List<AttendanceDTO> getAttendancesByDate(String date);

    void deleteAttendanceById(Long attendance_id);
}
