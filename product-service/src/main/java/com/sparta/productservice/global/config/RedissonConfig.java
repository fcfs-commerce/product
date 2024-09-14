package com.sparta.productservice.global.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {

  private static final String REDISSON_HOST_PREFIX = "redis://";

  @Value("${spring.data.redis.master.host}")
  private String masterHost;

  @Value("${spring.data.redis.master.port}")
  private int masterPort;

  @Value("${spring.data.redis.slave.host}")
  private String slaveHost;

  @Value("${spring.data.redis.slave.port}")
  private int slavePort;

  @Bean
  public RedissonClient redissonClient() {
    Config config = new Config();
    config.useReplicatedServers()
        .addNodeAddress(REDISSON_HOST_PREFIX + masterHost + ":" + masterPort)
        .addNodeAddress(REDISSON_HOST_PREFIX+slaveHost+":"+slavePort);
    return Redisson.create(config);
  }

}
