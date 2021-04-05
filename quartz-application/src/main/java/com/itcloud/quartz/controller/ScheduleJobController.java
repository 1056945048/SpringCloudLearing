package com.itcloud.quartz.controller;

import com.itcloud.quartz.module.PageUtils;
import com.itcloud.quartz.module.Result;
import com.itcloud.quartz.module.ScheduleJobEntity;
import com.itcloud.quartz.service.IScheduleJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author yangkun
 * @date 2021-03-24
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleJobController {
    @Autowired
    IScheduleJobService scheduleJobService;

    @GetMapping("")
    public Result list(@RequestParam Map<String,Object> params) {
        PageUtils page =scheduleJobService.queryPage(params);
        return Result.ok(page);
    }

    @PostMapping("")
    public Result save(@RequestBody ScheduleJobEntity scheduleJobEntity) {
        scheduleJobService.saveJob(scheduleJobEntity);
        return Result.ok();
    }
}
