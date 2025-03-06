package com.example.school.events;

import java.util.List;
import java.util.Optional;

public interface IEventsServices {
    
    Boolean isExist(EventsDTO eventsDTO);

    List<EventsDTO> addManyEvents(List<EventsDTO> eventsDTO);

    EventsDTO addEvent(EventsDTO eventsDTO);

    List<EventsDTO> getAllEvents();

    Optional<EventsDTO> getEventById(Long id);

    EventsDTO updateEvent(Long id,EventsDTO eventsDTO);

    void deleteEventById(Long id);

}
