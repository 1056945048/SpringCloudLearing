package com.itcloud.delay.queue.constants;

/**
 * @author yangkun
 * @date 2021-03-30
 */
public enum JobStatus {
    /**
     *不可执行状态，等待时钟周期
     */
    DELAY,
    /**
     *可执行状态，等待消费
     */
    READY,
    /**
     *已被消费者读取，但是未得到消费者的响应
     */
    RESERVED,
    /**
     *已被消费者完成或者已经删除
     */
    DELETED;
}
