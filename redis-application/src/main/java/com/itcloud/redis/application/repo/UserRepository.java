package com.itcloud.redis.application.repo;


import com.alibaba.fastjson.JSON;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import com.itcloud.redis.application.model.User;

import java.util.List;

/**
 * @author yangkun
 * @date 2021-03-20
 */
@Repository
public class UserRepository {
    private static final String USER = "USER";

    private HashOperations hashOperations;
    private RedisTemplate redisTemplate;

    public UserRepository(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = this.redisTemplate.opsForHash();
    }

    public void save(User user) {
        hashOperations.put(USER, user.getId(), JSON.toJSONString(user));
    }


    public String findById(String id) {
        return (String) hashOperations.get(USER, id);
    }

    public List<String> findAll() {
       return hashOperations.values(USER);
    }

    public void delete(String id) {
        hashOperations.delete(USER, id);
    }
}
