package com.itcloud.delay.queue.service;

import com.itcloud.delay.queue.constants.JobStatus;
import com.itcloud.delay.queue.container.DelayQueue;
import com.itcloud.delay.queue.container.JobPool;
import com.itcloud.delay.queue.container.ReadyQueue;
import com.itcloud.delay.queue.entity.DelayJob;
import com.itcloud.delay.queue.entity.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yangkun
 * @date 2021-03-30
 */
@Service
public class JobService {
    @Autowired
    DelayQueue delayQueue;

    @Autowired
    JobPool jobPool;

    @Autowired
    ReadyQueue readyQueue;

    public DelayJob addDefJob(Job request) {
        request.setJobStatus(JobStatus.DELAY);
        jobPool.addPool(request);
        DelayJob delayJob = new DelayJob(request);
        delayQueue.addDelayJob(delayJob);
        return delayJob;
    }

    public Job getProcessJob(String topic) {
        DelayJob delayJob = readyQueue.popJob(topic);
        if(delayJob == null || delayJob.getJobId() == 0L){
            return new Job();
        }
        Job job = jobPool.getJob(delayJob.getJobId());
        if(job == null){
            //元数据为空，则取下一个
            getProcessJob(topic);
        }else {
            //设置任务状态为已消费，但未得到响应
            job.setJobStatus(JobStatus.RESERVED);
            delayJob.setDelayDate(System.currentTimeMillis() + job.getTtrTime());

            jobPool.addPool(job);
            delayQueue.addDelayJob(delayJob);
        }
        return job;
    }

    public void finishJob(Long jobId) {
        jobPool.removeJob(jobId);
    }

    public void deleteJob(Long jobId) {
        jobPool.removeJob(jobId);
    }
}
