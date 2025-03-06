package com.example.school.announcements;

import java.util.List;
import java.util.Optional;

public interface IAnnouncementsServices {
    
    Boolean isExist(AnnouncementsDTO announcementsDTO);

    List<AnnouncementsDTO> addManyAnnouncements(List<AnnouncementsDTO> announcementsDTO);

    AnnouncementsDTO addAnnouncements(AnnouncementsDTO announcementsDTO);

    Optional<AnnouncementsDTO> getAnnouncementsById(Long id);

    List<AnnouncementsDTO> getAllAnnouncements();

    AnnouncementsDTO updateAnnouncements(Long id, AnnouncementsDTO announcementsDTO);

    void deleteAnnouncementsById(Long id);
}
