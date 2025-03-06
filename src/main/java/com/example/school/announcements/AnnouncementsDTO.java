package com.example.school.announcements;

import java.util.Date;

import com.example.school.classes.Classes;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnnouncementsDTO {
    Long id;
    String title;
    Classes classes;
    Date date;
}
