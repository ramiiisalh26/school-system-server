package com.example.school.security.cache;

import com.example.school.student.Student;
import com.example.school.student.StudentDTO;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisStudentCacheService {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisStudentCacheService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public StudentDTO getStudentById(Long id) {
        return (StudentDTO) redisTemplate.opsForValue().get("Student:"  + id);
    }

    public void saveStudent(Long id,StudentDTO student) {
        redisTemplate.opsForValue().set("Student:" + id,student,1, TimeUnit.HOURS);
    }

}
