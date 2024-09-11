package com.sparta.productservice.product.repository;

import com.sparta.productservice.global.redis.RedisRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class StockRepository extends RedisRepository<String, String> {

  private static final String PREFIX = "STOCK:";

  public StockRepository(RedisTemplate<String, String> redisTemplate) {
    super(PREFIX, redisTemplate);
  }

}
