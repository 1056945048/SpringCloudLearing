package com.itcloud.delay.queue.container;

import com.alibaba.fastjson.JSON;
import com.itcloud.delay.queue.entity.Job;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author yangkun
 * @date 2021-03-30
 */
@Slf4j
@Component
public class JobPool {

    @Autowired
    RedisTemplate redisTemplate;

    private String PoolName = "job.pool";

    public BoundHashOperations getPool() {
        BoundHashOperations ops = redisTemplate.boundHashOps(PoolName);
        return ops;
    }

    public void addPool(Job job) {
      log.info("任务池添加任务 ：{}"+ JSON.toJSONString(job));
      getPool().put(job.getId(),job);
    }

    public Job getJob(Long jobId) {
        Object o = getPool().get(jobId);
        if(o instanceof Job) {
           return (Job) o;
        }
        return null;
    }

    public void removeJob(Long jobId) {
        log.info("任务池移除任务 id ：{}" + jobId);
        getPool().delete(jobId);
    }

}
