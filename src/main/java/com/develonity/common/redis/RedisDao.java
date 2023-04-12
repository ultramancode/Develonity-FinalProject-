package com.develonity.common.redis;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

@Component
public class RedisDao {

  private final RedisTemplate<String, Object> redisTemplate;

  public RedisDao(RedisTemplate<String, Object> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

//  public void setValues(String key, Object data) {
//    ValueOperations<String, Object> values = redisTemplate.opsForValue();
//    values.set(key, data);
//  }

  public void setValues(String key, Object data, Duration duration) {
    ValueOperations<String, Object> values = redisTemplate.opsForValue();
    values.set(key, data, duration);
  }

  public Object getValues(String key) {
    ValueOperations<String, Object> values = redisTemplate.opsForValue();
    return values.get(key);
  }

  public void setBlackList(String key, String o, Long minutes){
    redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(o.getClass()));
    redisTemplate.opsForValue().set(key, o, minutes, TimeUnit.MINUTES);
  }
  public String getBlackList(String key) {
    return (String) redisTemplate.opsForValue().get(key);
  }

  public void deleteValues(String key) {
    redisTemplate.delete(key);
  }
}
