package com.example.school.announcements;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IAnnouncementsRespositry extends JpaRepository<Announcements, Long>{
    
}
