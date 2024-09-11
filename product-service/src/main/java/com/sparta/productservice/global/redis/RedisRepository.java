package com.sparta.productservice.global.redis;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;

@RequiredArgsConstructor
public abstract class RedisRepository<K, V> {

  private final String PREFIX;
  private final RedisTemplate<String, V> redisTemplate;

  public void setValue(K key, V value, Duration expiration) {
    redisTemplate.opsForValue().set(PREFIX + key, value, expiration);
  }

  public V getValueByKey(K key) {
    return redisTemplate.opsForValue().get(PREFIX + key);
  }

}
