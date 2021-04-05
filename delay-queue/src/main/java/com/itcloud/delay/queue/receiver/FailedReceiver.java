package com.itcloud.delay.queue.receiver;

import com.alibaba.fastjson.JSON;
import com.itcloud.delay.queue.config.FailedConfig;
import com.itcloud.delay.queue.entity.User;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author yangkun
 * @date 2021-03-29
 * 失败队列的订阅中一般会进行数据的持久化，以便后期人工介入进行处理
 */
@Slf4j
@Component
public class FailedReceiver {

    @RabbitListener(queues = FailedConfig.FAILED_QUEUE)
    public void receive(User user, Channel channel, Message message) {
        try{
            log.info("【FailedReceiver】收到消息：{}" + JSON.toJSONString(user));
            log.info(" 人工处理 ");
        } catch(Exception e) {
            log.info("receiver failed");
        }

    }
}
