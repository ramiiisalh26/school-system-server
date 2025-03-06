package com.example.school.announcements;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnnouncementsServicesImpl implements IAnnouncementsServices{

    private IAnnouncementsRespositry announcementsRespositry;

    @Autowired
    public AnnouncementsServicesImpl(final IAnnouncementsRespositry announcementsRespositry){
        this.announcementsRespositry = announcementsRespositry;
    }

    @Override
    public Boolean isExist(AnnouncementsDTO announcementsDTO) {
        return announcementsRespositry.existsById(announcementsDTO.getId());
    }

    @Override
    public List<AnnouncementsDTO> addManyAnnouncements(List<AnnouncementsDTO> announcementsDTO) {
        for (AnnouncementsDTO announcementsDTO2 : announcementsDTO) {
            addAnnouncements(announcementsDTO2);
        }
        return announcementsDTO;
    }

    @Override
    public AnnouncementsDTO addAnnouncements(AnnouncementsDTO announcementsDTO) {
        if (announcementsDTO == null) {
            throw new RuntimeException("Announcement must be provided");
        }

        Announcements announcements = AnnouncementsMapper.fromDTOToEntity(announcementsDTO);

        Announcements savedAnnouncements = announcementsRespositry.save(announcements);

        return AnnouncementsMapper.fromEntityToDTO(savedAnnouncements);
    }

    @Override
    public Optional<AnnouncementsDTO> getAnnouncementsById(Long id) {
        Optional<Announcements> announcement = announcementsRespositry.findById(id); 
        if (announcement.isPresent()) {
            return Optional.of(AnnouncementsMapper.fromEntityToDTO(announcement.get()));
        }
        return Optional.empty();
    }

    @Override
    public List<AnnouncementsDTO> getAllAnnouncements() {
        List<Announcements> announcements = announcementsRespositry.findAll();
        List<AnnouncementsDTO> announcementsDTO = announcements.stream().map(announcement -> AnnouncementsMapper.fromEntityToDTO(announcement)).collect(Collectors.toList());
        return announcementsDTO;
    }

    @Override
    public AnnouncementsDTO updateAnnouncements(Long id, AnnouncementsDTO announcementsDTO) {
        Announcements announcements = announcementsRespositry.findById(id).orElseThrow();
        if (announcements != null) {
            announcements.setClasses(announcementsDTO.getClasses());
            announcements.setDate(announcementsDTO.getDate());
            announcements.setTitle(announcementsDTO.getTitle());
            announcementsRespositry.save(announcements);
        }
        return AnnouncementsMapper.fromEntityToDTO(announcements);
    }

    @Override
    public void deleteAnnouncementsById(Long id) {
        announcementsRespositry.deleteById(id);
    }
    
}
