package com.itcloud.delay.queue.handler;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.RabbitListenerErrorHandler;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.stereotype.Component;

/**
 * @author yangkun
 * @date 2021-03-29
 */

@Slf4j
public class RetryReceiverListenerErrorHandler  implements RabbitListenerErrorHandler {

    @Override
    public Object handleError(Message message, org.springframework.messaging.Message<?> message1, ListenerExecutionFailedException e) throws Exception {
      log.info("接收消息发生错误，消息内容:{},错误信息：{}",
              JSON.toJSONString(message1.getPayload()),
              JSON.toJSONString(e.getStackTrace()));
      throw new AmqpRejectAndDontRequeueException("超出次数");
    }
}
