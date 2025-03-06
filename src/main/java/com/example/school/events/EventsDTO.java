package com.example.school.events;

import java.time.LocalTime;
import java.util.Date;

import com.example.school.classes.Classes;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EventsDTO {
    
    Long id;    
    String title;
    Classes classes;
    Date date;
    LocalTime startTime;
    LocalTime endTime;
}
