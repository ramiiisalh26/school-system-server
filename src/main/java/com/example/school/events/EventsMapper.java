package com.example.school.events;

public class EventsMapper {
    public static Events fromDTOToEntity(EventsDTO eventsDTO){
        return Events.builder()
            .id(eventsDTO.getId())
            .title(eventsDTO.getTitle())
            .classes(eventsDTO.getClasses())
            .date(eventsDTO.getDate())
            .startTime(eventsDTO.getStartTime())
            .endTime(eventsDTO.getEndTime())
            .build();
    }

    public static EventsDTO fromEntityToDTO(Events events){
        return EventsDTO.builder()
            .id(events.getId())
            .title(events.getTitle())
            .classes(events.getClasses())
            .date(events.getDate())
            .startTime(events.getStartTime())
            .endTime(events.getEndTime())
            .build();
    }
}
