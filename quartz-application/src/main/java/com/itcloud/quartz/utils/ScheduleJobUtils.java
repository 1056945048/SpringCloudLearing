package com.itcloud.quartz.utils;

import cn.hutool.json.JSONUtil;
import com.itcloud.quartz.module.JobConstant;
import com.itcloud.quartz.module.ScheduleJobEntity;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;


/**
 * @author yangkun
 * @date 2021-03-24
 */
@Slf4j
public class ScheduleJobUtils {
    private final static String JOB_NAME = "TASK_";
    /**
     * 创建定时任务
     * @param scheduler
     * @param scheduleJobEntity
     */
    public static void createJob(Scheduler scheduler, ScheduleJobEntity scheduleJobEntity) {
          log.info("创建定时任务", JSONUtil.toJsonStr(scheduleJobEntity));
          try{
              //构建job信息
              JobDetail jobDetail = JobBuilder.newJob(ScheduleJob.class).withIdentity(getJobKey(scheduleJobEntity.getId())).build();

              //表达式调度构建器
             CronScheduleBuilder scheduleBuilder =  CronScheduleBuilder.cronSchedule(scheduleJobEntity.getCronExpression()).withMisfireHandlingInstructionDoNothing();

             //按照新的cronExpression构造新的Trigger
             CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(getTriggerKey(scheduleJobEntity.getId()))
                                         .withSchedule(scheduleBuilder).build();

             //放入参数，运行时的方法可以获取
             jobDetail.getJobDataMap().put(JobConstant.JOB_PARAM_KEY,scheduleJobEntity);

             //返回值时下次要执行的时间
             scheduler.scheduleJob(jobDetail,trigger);

             //判断是否暂停任务
              if(scheduleJobEntity.getStatus() == JobConstant.ScheduleStatus.parse.getValue()) {
                    pauseJob(scheduler, scheduleJobEntity.getId());
              }
          } catch (Exception e) {
            e.printStackTrace();
          }
    }
    public static void pauseJob(Scheduler scheduler, String jobId) {
         log.info("暂停任务", jobId);

         try {
             scheduler.pauseJob(getJobKey(jobId));
         } catch (SchedulerException e) {
             e.printStackTrace();
         }

    }

    private static TriggerKey getTriggerKey(String jobId) {
        return TriggerKey.triggerKey(JOB_NAME + jobId);
    }

    private static JobKey getJobKey(String jobId) {
        return JobKey.jobKey(JOB_NAME + jobId);
    }



}
