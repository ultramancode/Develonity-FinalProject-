package com.develonity.cache;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

@Configuration
public class CacheConfig {

  @Bean
  public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {

    RedisCacheConfiguration defaultConfig = RedisCacheConfiguration.defaultCacheConfig()
        //모든 클래스 타입 Json형태로 저장 가능한 serializer
        .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));

    // 리소스 유형에 따라 만료 시간을 다르게 지정
    Map<String, RedisCacheConfiguration> redisCacheConfigMap = new HashMap<>();
    redisCacheConfigMap.put(CacheNames.LOGIN_ID, defaultConfig.entryTtl(Duration.ofHours(1)));
    redisCacheConfigMap.put(CacheNames.ALL_USERS, defaultConfig.entryTtl(Duration.ofHours(1))
        .serializeValuesWith(RedisSerializationContext
            .SerializationPair
            .fromSerializer(new JdkSerializationRedisSerializer())
        )
    );

    return RedisCacheManager.builder(connectionFactory)
        //여러 개의 CacheConfiguration 을 설정
        .withInitialCacheConfigurations(redisCacheConfigMap)
        .build();
  }
}


