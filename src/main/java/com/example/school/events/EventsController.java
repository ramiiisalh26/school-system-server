package com.example.school.events;

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
@RequestMapping("/api/v1/events")
public class EventsController {

    private IEventsServices eventsServices;

    @Autowired
    public EventsController(final IEventsServices eventsServices){
        this.eventsServices = eventsServices;
    }

    @PostMapping(path = "/add")
    public ResponseEntity<List<EventsDTO>> addEvents(@RequestBody final List<EventsDTO> eventsDTO){
        final List<EventsDTO> savedEvents =  eventsServices.addManyEvents(eventsDTO);
        return new ResponseEntity<List<EventsDTO>>(savedEvents, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<EventsDTO> getEventById(@PathVariable final Long id){
        Optional<EventsDTO> event = eventsServices.getEventById(id);
        if (event.isPresent()) {
            return new ResponseEntity<EventsDTO>(event.get(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<EventsDTO>> getAllEvents(){
        return new ResponseEntity<List<EventsDTO>>(eventsServices.getAllEvents(), HttpStatus.OK);
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<EventsDTO> updateEventns(@PathVariable final Long id, @RequestBody final EventsDTO eventsDTO){
        final Boolean isExist = eventsServices.isExist(eventsDTO);
        if (isExist) {
            final EventsDTO updatedEvent = eventsServices.updateEvent(id, eventsDTO);
            return new ResponseEntity<EventsDTO>(updatedEvent, HttpStatus.OK);
        }
        return new ResponseEntity<EventsDTO>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<EventsDTO> deleteEventById(@PathVariable final Long id){
        eventsServices.deleteEventById(id);
        return new ResponseEntity<EventsDTO>(HttpStatus.OK);
    }
}
