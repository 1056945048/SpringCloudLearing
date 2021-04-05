package com.itcloud.delay.queue.producer;

import com.itcloud.delay.queue.config.DelayConfig;
import com.itcloud.delay.queue.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * @author yangkun
 * @date 2021-03-29
 */

@Slf4j
@Component
public class DelaySender {
    @Autowired
    RabbitTemplate rabbitTemplate;

    public void send(User user) {
      log.info("消息已经发送，时间为：{}",new Timestamp(System.currentTimeMillis()));
      this.rabbitTemplate.convertAndSend(DelayConfig.DELAY_EXCHANGE,
                        //routing key
                        DelayConfig.DELAY_QUEUE,user);
    }

    /**
     * 对消息进行额外的设置，设置超时时间
     * 要想设置消息的超时时间，我们可以配置消息中expiration字段或者配置队列的x-message-ttl参数。
     * 但是需要注意的，当对队列和消息都设置的超时时间的时候，会选择最小的值作为实际超时时间。
     */
    public void send2(User user,Long time) {
        log.info("消息已经发送，时间为：{}",new Timestamp(System.currentTimeMillis()));
        this.rabbitTemplate.convertAndSend(
                DelayConfig.DELAY_EXCHANGE,
                DelayConfig.DELAY_QUEUE,
                user,
                message -> {
                    //设置延迟毫秒值
                    message.getMessageProperties().setExpiration(String.valueOf(time));
                    return message;
                });
    }


}
