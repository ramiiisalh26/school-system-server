package com.example.school.security.cache;

import com.example.school.result.Result;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisResultCacheService {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisResultCacheService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

//    public Result g
}
