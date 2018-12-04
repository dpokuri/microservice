package com.tl.booking.gateway.libraries.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.tl.booking.gateway.entity.configuration.RedisConfiguration;

@Configuration
@EnableCaching
public class CacheConfiguration {

  @Autowired
  RedisConfiguration redisConfiguration;

  @Bean
  public CacheManager cacheManager(RedisTemplate redisTemplate) {
    RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);

    return cacheManager;
  }

  @Bean
  JedisConnectionFactory jedisConnectionFactory() {
    JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
    jedisConnectionFactory.setHostName(redisConfiguration.getHost());
    jedisConnectionFactory.setPort(redisConfiguration.getPort());
    jedisConnectionFactory.setPassword(redisConfiguration.getPassword());
    return jedisConnectionFactory;
  }

  @Bean
  StringRedisSerializer stringRedisSerializer(){
    StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
    return stringRedisSerializer;
  }

  @Bean
  public RedisTemplate redisTemplate() {
    RedisTemplate template = new RedisTemplate();
    template.setConnectionFactory(jedisConnectionFactory());
    template.setKeySerializer(stringRedisSerializer());
    template.setHashKeySerializer(stringRedisSerializer());
    template.setValueSerializer(stringRedisSerializer());
    template.setHashValueSerializer(stringRedisSerializer());
    return template;
  }
}
