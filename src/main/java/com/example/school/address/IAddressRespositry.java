package com.example.school.address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import jakarta.transaction.Transactional;

public interface IAddressRespositry extends JpaRepository<Address,Long>{
    
    @Modifying
    @Transactional
    @Query("UPDATE Address a SET a.user.id = null WHERE a.user.id = :id")
    void setUserIdToBeNull(Long id);
}
