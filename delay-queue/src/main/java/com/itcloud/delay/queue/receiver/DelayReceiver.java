package com.itcloud.delay.queue.receiver;

import com.itcloud.delay.queue.config.DelayConfig;
import com.itcloud.delay.queue.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * @author yangkun
 * @date 2021-03-29
 */
@Slf4j
@Component
public class DelayReceiver {

    @RabbitListener(queues = DelayConfig.PROCESS_QUEUE)
    @RabbitHandler
    public void receiveDirect1(User user) {
       log.info("消息已经收到，时间为: {}",new Timestamp(System.currentTimeMillis()));
       System.out.println("【convert-receiveDirect1监听到消息】" +user);
    }
}
