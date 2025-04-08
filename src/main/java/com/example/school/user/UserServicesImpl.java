package com.example.school.user;

import java.security.Principal;
import java.util.Optional;

//import com.example.school.security.cache.RedisUserCacheService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServicesImpl implements IUserServices {

    private final PasswordEncoder passwordEncoder;
    private final IUserRepositry userRepositry;
//    private final RedisUserCacheService redisUserCacheService;
    @Autowired
    public UserServicesImpl(final PasswordEncoder passwordEncoder,
                            final IUserRepositry userRepositry
//                            final RedisUserCacheService redisUserCacheService
    ){
        this.userRepositry = userRepositry;
        this.passwordEncoder = passwordEncoder;
//        this.redisUserCacheService = redisUserCacheService;
    }

    @Transactional
    @Override
    public User signUp(User user) {

        boolean isUserExist = userRepositry.findByUsername(user.getUsername()).isPresent();

        if (isUserExist) {
            throw new IllegalStateException("Email already taken");
        }
        return userRepositry.save(user);
    }


    @Override
    public int enableUser(String username) {
        return userRepositry.enableUser(username);
    }

    @Override
    public boolean setVerifCode(String verifCode, Long id) {
        return userRepositry.setVerifCode(verifCode, id);
    }

//    @Override
//    public UserDetails getUser(String username) {
//        return userRepositry.findByUsername(username).orElseThrow();
//    }

    @Cacheable(value = "users",key = "#userId")
    @Override
    public Optional<User> getUserById(Long userId) {
//        User cachedUser = redisUserCacheService.getAutheticatedUserById(userId);
//        if (cachedUser != null) return Optional.of(cachedUser);

        User user = userRepositry.findById(userId).orElseThrow();

//        redisUserCacheService.saveAutheticatedUserById(userId, user);

        return Optional.of(user);
    }


    @Cacheable(value = "users",key = "#username")
    @Override
    public Optional<User> findByEmail(String username) {
//        User cachedUser = redisUserCacheService.getAutheticatedUserByUsername(username);
//        if (cachedUser != null) return Optional.of(cachedUser);

        User user = userRepositry.findByUsername(username).orElseThrow();

//        redisUserCacheService.saveAutheticatedUserByUsername(username,user);

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

//        if (redisUserCacheService.getAutheticatedUserById(id) != null) {
//            redisUserCacheService.removeAutheticatedUser(id);
//        }

        userRepositry.deleteById(id);
    }

}
