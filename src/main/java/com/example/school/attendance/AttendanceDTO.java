package com.example.school.attendance;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class AttendanceDTO {
    Long id;
    String student_code;
    String course_code;
    LocalDateTime date;
    String status;
}
