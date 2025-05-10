package com.aitrip.config;

import com.aitrip.database.dto.TokenResponseDTO;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {

    @Bean
    public CaffeineCacheManager cacheManager() {
        CaffeineCacheManager mgr = new CaffeineCacheManager("amadeusToken");
        mgr.setCaffeine(
                Caffeine.newBuilder()
                        .expireAfter(new Expiry<>() {
                            @Override
                            public long expireAfterCreate(
                                    Object key,
                                    Object value,
                                    long currentTime) {
                                TokenResponseDTO token = (TokenResponseDTO) value;
                                long ttl = token.getExpiresIn() - 60;
                                return TimeUnit.SECONDS.toNanos(Math.max(ttl, 0));
                            }

                            @Override
                            public long expireAfterUpdate(
                                    Object key,
                                    Object value,
                                    long currentTime,
                                    long currentDuration) {
                                return currentDuration;
                            }

                            @Override
                            public long expireAfterRead(
                                    Object key,
                                    Object value,
                                    long currentTime,
                                    long currentDuration) {
                                return currentDuration;
                            }
                        })
        );
        return mgr;
    }
}

