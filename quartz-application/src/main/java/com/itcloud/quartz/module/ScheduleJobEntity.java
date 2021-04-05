package com.itcloud.quartz.module;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author yangkun
 * @date 2021-03-24
 */
@Data

public class ScheduleJobEntity implements Serializable {
    private String id;
    private String beanName;
    private String params;
    private String cronExpression;
    private Integer status;
    private String remark;
    private Timestamp createTime;
}
