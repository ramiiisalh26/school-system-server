package com.example.school.announcements;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/announcements")
public class AnnouncementsController {
    
    private IAnnouncementsServices announcementsServices;

    @Autowired
    public AnnouncementsController(final IAnnouncementsServices announcementsServices){
        this.announcementsServices = announcementsServices;
    }

    @PostMapping(path = "/add")
    public ResponseEntity<List<AnnouncementsDTO>> addAnnouncements(@RequestBody final List<AnnouncementsDTO> announcementsDTO){
        final List<AnnouncementsDTO> savedAnnouncements = announcementsServices.addManyAnnouncements(announcementsDTO);
        return new ResponseEntity<List<AnnouncementsDTO>>(savedAnnouncements, HttpStatus.CREATED); 
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<AnnouncementsDTO> getAnnouncementsById(@PathVariable final Long id){
        final Optional<AnnouncementsDTO> announcement = announcementsServices.getAnnouncementsById(id);
        if (announcement.isPresent()) {
            return new ResponseEntity<AnnouncementsDTO>(announcement.get(), HttpStatus.OK);
        } 
        return new ResponseEntity<AnnouncementsDTO>(HttpStatus.OK);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<AnnouncementsDTO>> getAllAnnouncements(){
        return new ResponseEntity<List<AnnouncementsDTO>>(announcementsServices.getAllAnnouncements(), HttpStatus.OK);
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<AnnouncementsDTO> updateAnnouncements(@PathVariable final Long id, @RequestBody final AnnouncementsDTO announcementsDTO){
        final boolean isExist = announcementsServices.isExist(announcementsDTO);
        if (isExist) {
            final AnnouncementsDTO updateAnnouncements = announcementsServices.updateAnnouncements(id, announcementsDTO);
            return new ResponseEntity<AnnouncementsDTO>(updateAnnouncements, HttpStatus.OK);
        }
        return new ResponseEntity<AnnouncementsDTO>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<AnnouncementsDTO> deleteAnnouncementsById(@PathVariable final Long id){
        announcementsServices.deleteAnnouncementsById(id);
        return new ResponseEntity<AnnouncementsDTO>(HttpStatus.OK);
    }

}
