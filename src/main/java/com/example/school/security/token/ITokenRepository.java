package com.example.school.security.token;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface ITokenRepository extends JpaRepository<Token,Long>{
    
    Optional<Token> findByToken(String token);

    @Query("SELECT t from Token t JOIN User u ON t.user.id = u.id WHERE u.id = :id AND (t.expired = false OR t.revoked = false)")
    List<Token> findAllValidToken(Long id);
    
    @Query("SELECT t from Token t JOIN User u ON t.user.id = u.id WHERE u.id = :id")
    List<Token> findAllToken(Long id);

    @Modifying
    @Transactional
    @Query("DELETE FROM Token t WHERE t.user.id = :id")
    void deleteAllTokenByUserId(Long id);

    @Query("SELECT t from Token t JOIN User u ON t.user.id = u.id WHERE u.id = :id AND (t.expired = true AND t.revoked = true) ORDER BY t.id DESC LIMIT 1")
    public Token getCofirmedAt(Long id);

    @Transactional
    @Modifying
    @Query("UPDATE Token x SET x.confirmed_at = ?2 WHERE x.token = ?1")
    public int updateConfirmedAt(String token, Instant confirmedAt);

}
