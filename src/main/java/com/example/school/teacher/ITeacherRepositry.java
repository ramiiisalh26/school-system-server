package com.example.school.teacher;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import jakarta.transaction.Transactional;

public interface ITeacherRepositry extends JpaRepository<Teacher,Long>{

    @Modifying
    @Transactional
    @Query("UPDATE Teacher t SET t.address.id = null WHERE t.address.id = :id")
    void setAddressIdToBeNull(Long id);
}
