package com.itcloud.redis.application.repo;

import com.itcloud.redis.application.model.Rank;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * @author yangkun
 * @date 2021-03-20
 */
@Repository
public class RankRepository {
    private static final String RANK_KEY = "RANK";
    private ZSetOperations zSetOperations;
    private RedisTemplate redisTemplate;

    public RankRepository (RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        zSetOperations = this.redisTemplate.opsForZSet();
    }


    public void save(Rank rank) {
        zSetOperations.add(RANK_KEY, rank.getCity(), rank.getScore());
    }

    public Set<Rank> findAll() {
        return zSetOperations.reverseRangeWithScores(RANK_KEY,0,-1);
    }
}
