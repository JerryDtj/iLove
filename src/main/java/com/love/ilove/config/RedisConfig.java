package com.love.ilove.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 修改redis默认序列方式，默认是用JdkSerializationRedisSerializer,在保存近redis中时，会附带很多java类的信息
 * 造成阅读困难，而且占用空间，最关键的是increment 这个增加方法没法用（因为保存的不是数字）
 *
 * 把key和hashkey的序列化方式全部修改为StringRedisSerializer，因为key的基本上都是string的
 * 把val的序列化方式修改为GenericJackson2JsonRedisSerializer
 * 把hashVal的序列化方式修改为GenericToStringSerializer
 * @author Jerry
 * @Date 2019-06-10 09:48
 */
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericToStringSerializer<>(Object.class));
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
