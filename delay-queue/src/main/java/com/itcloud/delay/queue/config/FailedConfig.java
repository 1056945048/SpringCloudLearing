package com.itcloud.delay.queue.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yangkun
 * @date 2021-03-29
 * 重试次数超过上限后的消息处理队列，无额外处理
 */
@Configuration
public class FailedConfig {
    /**
     * 处理业务的队列
     */
    public final static String FAILED_QUEUE = "retry.failed.queue";

    /**
     * 处理业务的交换器
     */
    public final static String FAILED_EXCHANGE = "retry.failed.exchange";

    /**
     * 处理业务的路由key
     */
    public final static String FAILED_KEY = "retry.failed.key";


    /**
     * 处理业务的交换器
     * @return
     */
    @Bean
    DirectExchange retryFailedExchange() {
        return new DirectExchange(FAILED_EXCHANGE);
    }


    /**
     * 处理业务的队列
     * @return
     */
    @Bean
    public Queue retryFailedQueue() {
        return QueueBuilder
                .durable(FAILED_QUEUE)
                .build();
    }



    /**
     * 绑定处理队列的数据监听工作
     * @return
     */
    @Bean
    public Binding failedRetryBinding() {
        return BindingBuilder
                .bind(retryFailedQueue())
                .to(retryFailedExchange())
                .with(FAILED_KEY);
    }

}
