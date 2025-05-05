package com.example.school.attendance;

import java.sql.Date;
import java.time.LocalDateTime;

public class AttendanceMapper {

    public static AttendanceDTO fromEntityToDTO(Attendance attendance){
        return AttendanceDTO
                .builder()
                .id(attendance.getId())
                .status(String.valueOf(attendance.getStatus()))
                .date(attendance.getDate())
                .student_code(attendance.getStudent().getStudent_id())
                .course_code(attendance.getCourses().getCourse_code())
                .build();
    }
}
