package com.itcloud.quartz.utils;

import com.itcloud.quartz.module.JobConstant;
import com.itcloud.quartz.module.ScheduleJobEntity;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author yangkun
 * @date 2021-03-24
 */
@Slf4j
public class ScheduleJob extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
         ScheduleJobEntity jobEntity = (ScheduleJobEntity) jobExecutionContext.getMergedJobDataMap()
                                          .get(JobConstant.JOB_PARAM_KEY);
         try{
             log.info("准备执行任务，任务ID: " + jobEntity.getId());

             Object target = SpringContextUtils.getBean(jobEntity.getBeanName());
             Method method = target.getClass().getDeclaredMethod("run",String.class);
             method.invoke(target, jobEntity.getParams());

         } catch (NoSuchMethodException e) {
             e.printStackTrace();
         } catch (IllegalAccessException e) {
             e.printStackTrace();
         } catch (InvocationTargetException e) {
             e.printStackTrace();
         }
    }
}
