package com.example.school.security.token;

import java.time.Instant;

import com.example.school.user.Role;
import com.example.school.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_token")
public class Token {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 

    @Column(unique = true, nullable = false,columnDefinition = "NVARCHAR(3000)")
    private String token;

    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

    @Column(nullable = false)
    private Instant created_at;

    @Column(nullable = false)
    private Instant expire_at;

    @Column(nullable = true)
    private Instant confirmed_at;

    private boolean revoked;

    private boolean expired;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private Role role;
}
