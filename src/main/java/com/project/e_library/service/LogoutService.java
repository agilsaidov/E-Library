package com.project.e_library.service;

import com.project.e_library.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class LogoutService {

    private final RedisTemplate<String, String> redisTemplate;

    private static final String TOKEN_BLACKLIST_PREFIX = "blisted_token:";
    private static final int BLACKLIST_EXPIRATION = 60;

    public void logout(String token) {

        redisTemplate.opsForValue().set(TOKEN_BLACKLIST_PREFIX + token.substring(7),
                token.substring(7),
                BLACKLIST_EXPIRATION,
                TimeUnit.MINUTES
        );

    }
}
