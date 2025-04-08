package com.example.school.security.cache;

import com.example.school.user.User;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisUserCacheService {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisUserCacheService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void  saveAutheticatedUserById(Long userId, User user) {
        redisTemplate.opsForValue().set("AUTH_USER:" + userId, user,1, TimeUnit.HOURS);
    }

    public void saveAutheticatedUserByUsername(String username, User user) {
        redisTemplate.opsForValue().set("AUTH_USER:" + username, user, 1, TimeUnit.HOURS);
    }

    public User getAutheticatedUserById(Long userId) {
        return (User) redisTemplate.opsForValue().get("AUTH_USER:" + userId);
    }

    public User getAutheticatedUserByUsername(String username) {
        return (User) redisTemplate.opsForValue().get("AUTH_USER:" + username);
    }

    public void  removeAutheticatedUser(Long userId) {
        redisTemplate.delete("AUTH_USER:" + userId);
    }

}
