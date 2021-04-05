package com.itcloud.redis.application.repo;

import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author yangkun
 * @date 2021-03-20
 */
@Repository
public class PositionRepository {
    private static final String POSITION_KEY = "CHINA";
    private RedisTemplate redisTemplate;

    public PositionRepository(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    public Object position(String position) {
          String scriptStr = "return redis.call('GIS.CONTAINS', KEYS[1], ARGV[1])";
          return redisTemplate.getConnectionFactory().getConnection().eval(scriptStr.getBytes(), ReturnType.MULTI, 1,
                                            POSITION_KEY.getBytes(), position.getBytes());
    }

    public void save(String field, String value) {
        String scriptStr = "return redis.call('GIS.ADD', KEYS[1], ARGV[1], ARGV[2])";
        redisTemplate.getConnectionFactory().getConnection().eval(scriptStr.getBytes(), ReturnType.MULTI, 1,
                POSITION_KEY.getBytes(), field.getBytes(), value.getBytes());
    }

}
