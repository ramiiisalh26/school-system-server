package com.example.school.classes;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {
    List<DayOfWeek> days;
    LocalTime startTime;
    LocalTime endTime;

}
