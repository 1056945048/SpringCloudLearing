package com.itcloud.quartz.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.itcloud.quartz.module.PageUtils;
import com.itcloud.quartz.module.ScheduleJobEntity;

import java.util.Map;

/**
 * @author yangkun
 * @date 2021-03-24
 */

public interface IScheduleJobService extends IService<ScheduleJobEntity> {
    void saveJob(ScheduleJobEntity scheduleJobEntity);

    PageUtils queryPage(Map<String, Object> params);
}
