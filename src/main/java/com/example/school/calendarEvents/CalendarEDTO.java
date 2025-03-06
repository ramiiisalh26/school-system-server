package com.example.school.calendarEvents;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CalendarEDTO {
    Long id;
    String title;
    boolean allDay;
    Date start_time;
    Date end_time;
}
