package com.example.school.user;

import java.security.Principal;
import java.util.Optional;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServicesImpl implements IUserServices {



    private final PasswordEncoder passwordEncoder;
    private final IUserRepositry userRepositry;

    @Autowired
    public UserServicesImpl(final PasswordEncoder passwordEncoder, final IUserRepositry userRepositry) {
        this.userRepositry = userRepositry;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public User signUp(User user) {

        boolean isUserExist = userRepositry.findByUsername(user.getUsername()).isPresent();

        if (isUserExist) {
            throw new IllegalStateException("Email already taken");
        }
        User savedUser = userRepositry.save(user);
        return savedUser;
    }


    @Override
    public int enableUser(String username) {
        return userRepositry.enableUser(username);
    }

    @Override
    public boolean setVerifCode(String verifCode, Long id) {
        return userRepositry.setVerifCode(verifCode, id);
    }

    @Override
    public UserDetails getUser(String username) {
        return userRepositry.findByUsername(username).orElseThrow();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepositry.findById(id);
    }


    @Override
    public Optional<User> findByEmail(String username) {
        User user = userRepositry.findByUsername(username).get();
        if (user == null) {
            return Optional.empty();
        }
        return Optional.of(user);
    }

    @Override
    public Boolean isEnabled(String username) {
        return userRepositry.getEnableUser(username);
    }

    @Override
    public void changePassword(ChangePasswordRequest changePasswordRequest, Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        // check is the current password is correct
        if (!passwordEncoder.matches(changePasswordRequest.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong Password");
        }

        // Check if the two new Passwords are the same
        if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmationPassword())) {
            throw new IllegalStateException("Password are not same");
        }

        // update the Password
        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));

        userRepositry.save(user);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepositry.deleteById(id);
    }

}
