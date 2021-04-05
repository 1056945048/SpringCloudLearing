package com.itcloud.delay.queue.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yangkun
 * @date 2021-03-29
 */
@Configuration
public class DelayConfig {
    //正常的消费队列
    public static final String DELAY_QUEUE = "delay.queue";

    public static final String DELAY_EXCHANGE = "delay.queue.exchange";

    private static Long QUEUE_EXPIRATION = 4000L;

    @Bean
    DirectExchange delayExchange() {
        return new DirectExchange(DELAY_EXCHANGE);
    }

    @Bean
    public Queue delayQueue() {
        return QueueBuilder.durable(DELAY_QUEUE) //配置消息超时后称为死信消息需要进入的队列和交换器
                //DLX,dead letter发送到的exchange,设置死信队列交换器到处理交换器
                .withArgument("x-dead-letter-exchange",PROCESS_EXCHANGE)
                //dead letter携带的routing key ,配置处理队列的路由key
                .withArgument("x-dead-letter-routing-key", PROCESS_QUEUE)
                //设置队列过期时间
                .withArgument("x-message-ttl", QUEUE_EXPIRATION)
                .build();
    }
    @Bean
    Binding queueTTLBinding() {
        return BindingBuilder
                .bind(delayQueue())
                .to(delayExchange())
                .with(DELAY_QUEUE);

    }

    //-----------------死信队列配置-----------------------------

    /**
     * 实际消费队列
     * message失效后进入的队列，也就是实际的消费队列
     */
    public static final String PROCESS_QUEUE = "process.queue";
    /**
     * 处理的交换器
     */
    public static final String PROCESS_EXCHANGE = "process.queue.exchange";


    @Bean
    public Queue delayProcess() {
        return QueueBuilder.durable(PROCESS_QUEUE).build();
    }

    @Bean
    public DirectExchange processExchange() {
        return new DirectExchange(PROCESS_EXCHANGE);
    }

    /**
     * 实际的死信队列
     * @return
     */
    @Bean
    Binding processBinding() {
         return BindingBuilder
                 .bind(delayProcess())
                 .to(processExchange())
                 .with(PROCESS_QUEUE);
    }
}
