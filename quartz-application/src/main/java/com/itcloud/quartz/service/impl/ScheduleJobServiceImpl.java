package com.itcloud.quartz.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itcloud.quartz.mapper.ScheduleJobMapper;
import com.itcloud.quartz.module.PageUtils;
import com.itcloud.quartz.module.Query;
import com.itcloud.quartz.module.ScheduleJobEntity;
import com.itcloud.quartz.service.IScheduleJobService;
import com.itcloud.quartz.utils.ScheduleJobUtils;
import org.apache.commons.lang.StringUtils;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;

/**
 * @author yangkun
 * @date 2021-03-24
 */
@Service
public class ScheduleJobServiceImpl extends ServiceImpl<ScheduleJobMapper,ScheduleJobEntity> implements IScheduleJobService {

    @Autowired
    private Scheduler scheduler;

    @Override
    public void saveJob(ScheduleJobEntity scheduleJobEntity) {
        scheduleJobEntity.setCreateTime(DateUtil.date().toTimestamp());
        this.save(scheduleJobEntity);
        ScheduleJobUtils.createJob(scheduler,scheduleJobEntity);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String beanName = (String)params.get("beanName");

        IPage<ScheduleJobEntity> page = this.page(new Query<ScheduleJobEntity>(params).getPage(),
                Wrappers.<ScheduleJobEntity>lambdaQuery()
                          .eq(StringUtils.isNotEmpty(beanName),ScheduleJobEntity::getBeanName, beanName)
                          .orderByDesc(ScheduleJobEntity::getCreateTime));

        return new PageUtils(page);
    }
}
