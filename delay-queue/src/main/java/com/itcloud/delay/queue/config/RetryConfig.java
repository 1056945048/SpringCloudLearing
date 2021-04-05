package com.itcloud.delay.queue.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yangkun
 * @date 2021-03-29
 * 延迟队列，设置其死信队列为workqueue
 */
@Configuration
public class RetryConfig {
    public static final String RETRY_QUEUE = "retry_queue";
    public static final String RETRY_EXCHANGE = "retry_exchange";
    public static final String RETRY_KEY = "retry_key";
    /**
     * 超时时间
     */
    public static final Long QUEUE_EXPIRATION = 4000L;


    /*配置了死信相关参数，死信队列为workqueue*/
    @Bean
    public Queue retryQueue() {
        return QueueBuilder.durable(RETRY_QUEUE)
                     .withArgument("x-dead-letter-exchange",WorkConfig.WORK_EXCHANGE)
                     .withArgument("x-dead-letter-routing-key",WorkConfig.WORK_KEY)
                     .withArgument("x-message-ttl",QUEUE_EXPIRATION)
                     .build();
    }

    @Bean
    public DirectExchange retryExchange() {
        return new DirectExchange(RETRY_EXCHANGE);
    }

    @Bean
    Binding retryBinding() {
        return BindingBuilder.bind(retryQueue()).to(retryExchange()).with(RETRY_KEY);
    }
}
