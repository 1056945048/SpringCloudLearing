package com.itcloud.redis.application.repo;

import com.itcloud.redis.application.model.HotArea;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Repository;

/**
 * @author yangkun
 * @date 2021-03-20
 */
@Repository
public class HotAreaRepository {
    private static final String EAREAKEY ="EAREA";
    private SetOperations setOperations;
    private RedisTemplate redisTemplate;

    public HotAreaRepository(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.setOperations = this.redisTemplate.opsForSet();
    }
    public void save(HotArea hotArea) {
        setOperations.add(EAREAKEY, hotArea.getName());
    }
    public Boolean contains(String name) {
        return setOperations.isMember(EAREAKEY, name);
    }
}
