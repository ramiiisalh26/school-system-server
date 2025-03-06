package com.example.school.calendarEvents;

public class CalendarEMapper {
    public static CalendarE fromDTOToEntity(CalendarEDTO calendarEDTO){
        return CalendarE.builder()
            .id(calendarEDTO.getId())
            .title(calendarEDTO.getTitle())
            .allDay(calendarEDTO.isAllDay())
            .start_time(calendarEDTO.getStart_time())
            .end_time(calendarEDTO.getEnd_time())
            .build();
    }

    public static CalendarEDTO fromEntityToDTO(CalendarE calendarE){
        return CalendarEDTO.builder()
            .id(calendarE.getId())
            .title(calendarE.getTitle())
            .allDay(calendarE.isAllDay())
            .start_time(calendarE.getStart_time())
            .end_time(calendarE.getEnd_time())
            .build();
    }
}
