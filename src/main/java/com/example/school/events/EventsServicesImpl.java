package com.example.school.events;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventsServicesImpl implements IEventsServices{

    private IEventsRepositry eventsRepositry;

    @Autowired
    public EventsServicesImpl(final IEventsRepositry eventsRepositry){
        this.eventsRepositry = eventsRepositry;
    }

    @Override
    public Boolean isExist(EventsDTO eventsDTO) {
        return eventsRepositry.existsById(eventsDTO.getId());
    }

    @Override
    public List<EventsDTO> addManyEvents(List<EventsDTO> eventsDTO) {
        for (EventsDTO eventsDTO2 : eventsDTO) {
            addEvent(eventsDTO2);
        }
        return eventsDTO;
    }

    @Override
    public EventsDTO addEvent(EventsDTO eventsDTO) {
        
        if (eventsDTO == null) {
            throw new RuntimeException("Event Must be Provided");
        }

        Events events = EventsMapper.fromDTOToEntity(eventsDTO);

        Events savedEvents = eventsRepositry.save(events);

        return EventsMapper.fromEntityToDTO(savedEvents);
    }

    @Override
    public List<EventsDTO> getAllEvents() {
        List<Events> events = eventsRepositry.findAll();
        List<EventsDTO> eventsDTO = events.stream().map(event -> EventsMapper.fromEntityToDTO(event)).collect(Collectors.toList());
        return eventsDTO; 
    }

    @Override
    public Optional<EventsDTO> getEventById(Long id) {
        Optional<Events> event = eventsRepositry.findById(id);
        if (event.isPresent()) {
            return Optional.of(EventsMapper.fromEntityToDTO(event.get()));
        }

        return Optional.empty();
    }

    @Override
    public EventsDTO updateEvent(Long id, EventsDTO eventsDTO) {
        Events events = eventsRepositry.findById(id).orElseThrow();
        if (events != null) {
            events.setClasses(eventsDTO.getClasses());
            events.setDate(eventsDTO.getDate());
            events.setEndTime(eventsDTO.getEndTime());
            events.setStartTime(eventsDTO.getStartTime());
            events.setTitle(events.getTitle());
            eventsRepositry.save(events);
        }
        return EventsMapper.fromEntityToDTO(events);
    }

    @Override
    public void deleteEventById(Long id) {
        eventsRepositry.deleteById(id);
    }
    
}
