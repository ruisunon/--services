package ai.sxr.shoppingla.auth.service;

import ai.sxr.shoppingla.config.RedisCacheConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TokenBlackListingService {

    private final RedisTemplate redisTemplate;

    @Cacheable(value = RedisCacheConfig.BLACKLIST_CACHE_NAME, key = "#jwt")
//    @Caching(put = {@CachePut(value = RedisCacheConfig.BLACKLIST_CACHE_NAME,key = "#jwt")})
    public String blackListJwt(String jwt) {
        return jwt;
    }

    @Cacheable(value = RedisCacheConfig.BLACKLIST_CACHE_NAME, key = "#jwt", unless = "#result == null")
    public String getJwtBlackList(String jwt) {
        return null;
    }

    public String blackListJwtWithExpiryTime(String jwt, long timeLeft) {
        redisTemplate.opsForHash().put(jwt, "", RedisCacheConfig.BLACKLIST_CACHE_NAME);
        redisTemplate.expire(jwt, timeLeft, TimeUnit.SECONDS);
        return jwt;
    }

}