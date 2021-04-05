package com.itcloud.redis.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author yangkun
 * @date 2021-03-20
 */
@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory connectionFactory) {
           RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
           redisTemplate.setDefaultSerializer(new StringRedisSerializer());
           redisTemplate.setEnableDefaultSerializer(true);
           redisTemplate.setConnectionFactory(connectionFactory);
           return redisTemplate;
    }
}
