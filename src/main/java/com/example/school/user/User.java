package com.example.school.user;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(
    name = "_user",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "username_unique",
            columnNames = "username"
        )
    }
)
public class User implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
        name = "first_name",
        nullable = false
    )
    private String first_name;

    @Column(
        name = "last_name",
        nullable = false
    )
    private String last_name;

    @Column(
        name = "username",
        nullable = false
    )
    private String username;

    @Column(
        name = "password",
        nullable = false
    )
    private String password;

    @Column(nullable = true)
    private String verifCode;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean locked = false;
    private boolean enabled = false;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired(){
        return true;
    }

    @Override 
    public boolean isAccountNonLocked(){
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired(){
        return true;
    }

    @Override
    public boolean isEnabled(){
        return enabled;
    }
    
}
