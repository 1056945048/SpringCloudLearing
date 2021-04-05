package com.itcloud.delay.queue.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yangkun
 * @date 2021-03-29
 * 加入消息重试的功能
 */
@Configuration
public class WorkConfig {
    public final static String WORK_QUEUE = "retry.work.queue";
    public final static String WORK_EXCHANGE = "retry.work.exchange";
    public final static String WORK_KEY = "retry.work.key";

    @Bean
    DirectExchange retryWorkExchange(){
        return new DirectExchange(WORK_EXCHANGE);
    }
    @Bean
    public Queue retryWorkQueue() {
        return QueueBuilder.durable(WORK_QUEUE).build();
    }

    /**
     * 绑定处理队列的数据监听工作
     * @return
     */
    @Bean
    Binding workRetryBinding() {
        return BindingBuilder.bind(retryWorkQueue()).to(retryWorkExchange()).with(WORK_KEY);
    }
}
