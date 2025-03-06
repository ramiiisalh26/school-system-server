package com.example.school.events;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IEventsRepositry extends JpaRepository<Events,Long>{
    
}
