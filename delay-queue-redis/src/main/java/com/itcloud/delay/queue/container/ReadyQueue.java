package com.itcloud.delay.queue.container;

import com.alibaba.fastjson.JSON;
import com.itcloud.delay.queue.entity.DelayJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author yangkun
 * @date 2021-03-30
 */
@Slf4j
@Component
public class ReadyQueue {
    @Autowired
    RedisTemplate redisTemplate;

    private String READY_NAME = "process.queue";

    private String getKey(String topic) {
        return READY_NAME + topic;
    }

    /**
     * 根据不同的消息类型得到不同的List集合
     * @param topic
     */
     public BoundListOperations getQueue(String topic) {
         BoundListOperations boundListOperations = redisTemplate.boundListOps(getKey(topic));
         return boundListOperations;
     }

    public void pushJob(DelayJob delayJob) {
        BoundListOperations listOperations = getQueue(delayJob.getTopic());
        listOperations.leftPush(delayJob);
    }

    //controller层取出任务
    public DelayJob popJob(String topic) {
        BoundListOperations listOperations = getQueue(topic);
        Object o = listOperations.leftPop();
        if( o instanceof DelayJob) {
            log.info("取出任务：{}", JSON.toJSONString((DelayJob) o));
            return (DelayJob) o;
        }
        return null;
    }
}
