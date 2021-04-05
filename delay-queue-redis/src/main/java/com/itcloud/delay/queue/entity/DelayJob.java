package com.itcloud.delay.queue.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author yangkun
 * @date 2021-03-30
 */
@Data
@AllArgsConstructor
public class DelayJob implements Serializable {
    /**
     * 延迟任务的唯一标识
     */
    private long jobId;
    /**
     * 任务的执行时间 = 当前时间 + 需要延迟的时间
     */
    private long delayDate;
    /**
     * 任务类型（具体业务类型）
     */
    private String topic;

    public DelayJob(Job job) {
        this.jobId = job.getId();
        this.delayDate = System.currentTimeMillis() + job.getDelayTime();
        this.topic = job.getTopic();
    }



}
