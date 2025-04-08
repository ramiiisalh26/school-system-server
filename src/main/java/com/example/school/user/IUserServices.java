package com.example.school.user;

import java.security.Principal;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;

public interface IUserServices {

    User signUp(User user);
    
//    UserDetails getUser(String username);

    Optional<User> getUserById(Long id);

    int enableUser(String username);

    Optional<User> findByEmail(String username);

    Boolean isEnabled(String username);

    void changePassword(ChangePasswordRequest changePasswordRequest, Principal connectedUser);

    boolean setVerifCode(String verifCode, Long id);

    void deleteUserById(Long id);
} 
