package com.itcloud.delay.queue.handler;

import com.alibaba.fastjson.JSON;
import com.itcloud.delay.queue.constants.DelayConfig;
import com.itcloud.delay.queue.constants.JobStatus;
import com.itcloud.delay.queue.container.DelayQueue;
import com.itcloud.delay.queue.container.JobPool;
import com.itcloud.delay.queue.container.ReadyQueue;
import com.itcloud.delay.queue.entity.DelayJob;
import com.itcloud.delay.queue.entity.Job;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author yangkun
 * @date 2021-03-30
 */
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class DelayJobHandler implements Runnable {

    private JobPool jobPool;
    private DelayQueue delayQueue;
    private ReadyQueue readyQueue;
    //对bucket所在的下标
    private int index;

    @Override
    public void run() {
      log.info("定时任务开始执行");
      while(true) {
          try{
              DelayJob delayJob = delayQueue.getFirstDelayJob(index);
              //没有延迟任务
              if(delayJob == null) {
                  sleep();
                  continue;
              }
              //发现延时任务，并检查时间
              if(delayJob.getDelayDate() > System.currentTimeMillis()) {
                  //延时时间没到
                  sleep();
                  continue;
              }
              Job job = jobPool.getJob(delayJob.getJobId());

              if(job == null) {
                 log.info("移除不存在任务{}", JSON.toJSONString(delayJob));
                 delayQueue.removeDelayTime(index,delayJob);
                 continue;
              }
              JobStatus status = job.getJobStatus();
              if(JobStatus.RESERVED.equals(status)) {
                  log.info("处理超时任务:{}", JSON.toJSONString(job));
                  //超时任务
                  processTTrJob(delayJob,job);
              }else {
                  log.info("处理延时任务:{}", JSON.toJSONString(job));
                  //延时任务
                  processDelayJob(delayJob,job);
              }
          }catch (Exception e) {
              log.error("扫描DelayBucket出错：",e.getStackTrace());
              sleep();
          }
      }

    }
    //处理超时任务
    private void processTTrJob(DelayJob delayJob, Job job) {
         //修改job状态
         job.setJobStatus(JobStatus.DELAY);
         //更改任务池中的job
         jobPool.addPool(job);
         //移除原先延迟队列中的任务
         delayQueue.removeDelayTime(index,delayJob);
         //重新计算
         delayJob.setDelayDate(System.currentTimeMillis() + job.getDelayTime());
         //重新放入延迟队列
        delayQueue.addDelayJob(delayJob);
    }

    //正常处理延时任务，
    private void processDelayJob(DelayJob delayJob, Job job) {
         job.setJobStatus(JobStatus.READY);
         jobPool.addPool(job);
         //delayQueue移除
         delayQueue.removeDelayTime(index,delayJob);
         //放入到readyQueue
         readyQueue.pushJob(delayJob);
    }

    private void sleep()  {
        try {
            Thread.sleep(DelayConfig.SLEEP_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
