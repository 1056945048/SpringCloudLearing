package com.itcloud.delay.queue.entity;

import com.itcloud.delay.queue.constants.JobStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author yangkun
 * @date 2021-03-30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Job implements Serializable {
    //消息唯一标识
    private Long id;

    //job类型，理解为具体的业务名称
    private String topic;

    private long delayTime;

    private long ttrTime;

    //Job的内容，供消费者做具体的业务处理
    private String message;

    //Job当前所处的状态
    private JobStatus jobStatus;
}
