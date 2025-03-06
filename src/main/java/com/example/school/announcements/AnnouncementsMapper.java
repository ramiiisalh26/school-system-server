package com.example.school.announcements;

public class AnnouncementsMapper {
    public static Announcements fromDTOToEntity(AnnouncementsDTO announcementsDTO){
        return Announcements.builder()
            .id(announcementsDTO.getId())
            .title(announcementsDTO.getTitle())
            .date(announcementsDTO.getDate())
            .classes(announcementsDTO.getClasses())
            .build();
    }

    public static AnnouncementsDTO fromEntityToDTO(Announcements announcements){
        return AnnouncementsDTO.builder()
            .id(announcements.getId())
            .title(announcements.getTitle())
            .date(announcements.getDate())
            .classes(announcements.getClasses())
            .build();
    }
}
