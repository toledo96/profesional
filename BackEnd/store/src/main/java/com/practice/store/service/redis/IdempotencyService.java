package com.practice.store.service.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.function.Supplier;

@Service
public class IdempotencyService {
    private final RedisTemplate<String, Object> redisTemplate;

    public IdempotencyService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public <T> T process(String key, Supplier<T> action) {
        // Verificar si ya existe en Redis
        if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            return (T) redisTemplate.opsForValue().get(key);
        }

        // Ejecutar acción y guardar resultado
        T result = action.get();
        redisTemplate.opsForValue().set(key, result, Duration.ofMinutes(10)); // TTL opcional
        return result;
    }

}
