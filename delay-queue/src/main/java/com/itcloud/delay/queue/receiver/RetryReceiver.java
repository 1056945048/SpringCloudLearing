package com.itcloud.delay.queue.receiver;

import com.alibaba.fastjson.JSON;
import com.itcloud.delay.queue.config.FailedConfig;
import com.itcloud.delay.queue.config.RetryConfig;
import com.itcloud.delay.queue.config.WorkConfig;
import com.itcloud.delay.queue.entity.User;
import com.rabbitmq.client.AMQP;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author yangkun
 * @date 2021-03-29
 * 消息重试监听者：    需要进行消息重试或者转发至失败队列
 */
@Component
@Slf4j
public class RetryReceiver {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = WorkConfig.WORK_QUEUE)
    public void receive(User user, AMQP.Channel channel, Message message) {
        try{
            log.info("【WorkReceiver监听到消息】" + JSON.toJSONString(user));
            int retry = user.getRetry();
            log.info("重试次数:{}",retry);
            if(retry < 3 ) {
                user.setRetry(retry + 1);
                throw new RuntimeException("进入重试");
            }
            log.info("消费成功");
        } catch (Exception e) {
            log.info("开始重试");
            if(user.getRetry() > 3) {
                rabbitTemplate.convertAndSend(FailedConfig.FAILED_EXCHANGE,
                                      FailedConfig.FAILED_KEY,
                                      user);
            log.info("receiver failed");
            } else {
                rabbitTemplate.convertAndSend(RetryConfig.RETRY_EXCHANGE,
                                       RetryConfig.RETRY_KEY,
                                       user);
            log.info("receive error");
            }
        }
    }
}
