package com.example.school.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface IUserRepositry extends JpaRepository<User, Long>{

    Optional<User> findByUsername(String username);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.enabled = TRUE, u.locked = FALSE WHERE u.username = ?1")
    public int enableUser(String username);

    @Query("SELECT u.enabled FROM User u WHERE u.username = ?1")
    public Boolean getEnableUser(String username);

    @Query("UPDATE User u SET u.verifCode = ?1 WHERE u.id = ?2")
    public Boolean setVerifCode(String verifCode, Long id);
}
