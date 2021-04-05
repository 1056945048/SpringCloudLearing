package com.itcloud.delay.queue.container;


import com.alibaba.fastjson.JSON;
import com.itcloud.delay.queue.entity.DelayJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yangkun
 * @date 2021-03-30
 */
@Slf4j
@Component
public class DelayQueue {
    @Autowired
    RedisTemplate redisTemplate;

    private AtomicInteger index = new AtomicInteger(0);
    @Value("${thread.size}")
    private int bucketSize;

    List<String> bucketsName = new ArrayList<>();

    @Bean
    public List<String> createBuckets() {
        for(int i=0;i<bucketSize;i++) {
            bucketsName.add("bucket" + i);
        }
        return bucketsName;
    }

    /**
     * 获取桶集合名称
     * @return
     */
    public String getThisBucketName() {
        int thisIndex = index.addAndGet(1);
        int i = thisIndex % bucketSize;
        return bucketsName.get(i);
    }

    /**
     * 每一个bucket中是一个有序集合，所以采用redis中的zset集合
     * @return
     */
    public BoundZSetOperations getBucket(String bucketName) {
        BoundZSetOperations zset = redisTemplate.boundZSetOps(bucketName);
        return zset;
    }

    /**
     * 添加任务到延迟队列，这里的下标由类中的原子类决定
     * @param delayJob
     */
    public void addDelayJob(DelayJob delayJob) {
       log.info("添加延迟任务{}", JSON.toJSONString(delayJob));
       String thisBucketName = getThisBucketName();
        BoundZSetOperations bucketZset = getBucket(thisBucketName);
        //zset会按照延迟时间进行排序，将实际执行时间短（score）的放在前面
        bucketZset.add(delayJob,delayJob.getDelayDate());
    }


    //取出bucket中最新的延时任务，只取一个
    public DelayJob getFirstDelayJob(int index) {
        String bucketName = bucketsName.get(index);
        BoundZSetOperations bucketZSet = getBucket(bucketName);
        Set<ZSetOperations.TypedTuple> set = bucketZSet.rangeWithScores(0, 1);
        if(CollectionUtils.isEmpty(set)) {
            return null;
        }
        ZSetOperations.TypedTuple typedTuple = (ZSetOperations.TypedTuple)set.toArray()[0];
        Object value = typedTuple.getValue();
        if(value instanceof DelayJob) {
            return (DelayJob) value;
        }
        return null;
    }

    public void removeDelayTime(int index, DelayJob delayJob) {
        String bucketName = bucketsName.get(index);
        BoundZSetOperations setOperations = getBucket(bucketName);
        setOperations.remove(delayJob);
    }
}
