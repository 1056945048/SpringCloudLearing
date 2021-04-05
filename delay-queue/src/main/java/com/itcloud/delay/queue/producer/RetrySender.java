package com.itcloud.delay.queue.producer;

import com.itcloud.delay.queue.config.WorkConfig;
import com.itcloud.delay.queue.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author yangkun
 * @date 2021-03-29
 * 消息重试的发送者
 */
@Slf4j
@Component
public class RetrySender {
    @Autowired
    RabbitTemplate rabbitTemplate;

    public void send(User user) {
      this.rabbitTemplate.convertAndSend(WorkConfig.WORK_EXCHANGE
              //route key
              ,WorkConfig.WORK_KEY
              ,user);
    }
}
