package com.tl.booking.promo.code.service.impl;

import com.tl.booking.promo.code.service.api.CacheService;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class CacheServiceImpl implements CacheService {

  private static final Logger LOGGER = LoggerFactory.getLogger(CacheServiceImpl.class);

  @Autowired
  RedisTemplate redisTemplate;

  @Override
  public <T> T findCacheByKey(String key, Class<T> clazz) {
    T object = null;

    try {
      Object cacheValue = this.redisTemplate.opsForValue().get(key);

      if (!isNullCacheValue(cacheValue)) {
        object = clazz.cast(cacheValue);
      }
    } catch (Exception e) {
      LOGGER.info(String.valueOf(e));
    }

    return object;
  }

  private boolean isNullCacheValue(Object cacheValue) {
    Boolean result = false;
    if (cacheValue == null) {
      result = true;
    }

    return result;
  }

  @Override
  public Boolean createCache(String key, Object value, long expirySeconds) {
    Boolean success = true;

    try {
      if (expirySeconds == 0) {
        this.redisTemplate.opsForValue().set(key, value);
      } else {
        this.redisTemplate.opsForValue().set(key, value, expirySeconds, TimeUnit.SECONDS);
      }
    } catch (Exception e) {
      LOGGER.info(String.valueOf(e));
      success = false;
    }

    return success;
  }

  @Override
  public Boolean deleteCache(String key) {
    Boolean success = true;

    try {
      this.redisTemplate.delete(key);
    } catch (Exception e) {
      LOGGER.info(String.valueOf(e));
      success = false;
    }

    return success;
  }
}
