package com.itcloud.delay.queue.controller;

import com.alibaba.fastjson.JSON;
import com.itcloud.delay.queue.entity.DelayJob;
import com.itcloud.delay.queue.entity.Job;
import com.itcloud.delay.queue.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yangkun
 * @date 2021-03-30
 */
@RestController
@RequestMapping("/delay")
public class DelayController {
    @Autowired
    private JobService jobService;

    private final static AtomicInteger index = new AtomicInteger(0);

    private final static String[] tag = new String[]{"test","web","java"};


    /**
     * 添加 测试的时候使用
     * @return
     */
    @RequestMapping(value = "addTest",method = RequestMethod.POST)
    public String addDefJobTest() {
        Job request = new Job();
        int i = index.addAndGet(1);
        Long aLong = Long.valueOf(i);
        request.setId(aLong);
        int num = i%3;
        request.setTopic(tag[num]);
        request.setMessage("tag:" + tag[num] + "id:" + i);
        request.setDelayTime(10000);
        request.setTtrTime(10000);
        DelayJob delayJob = jobService.addDefJob(request);
        return JSON.toJSONString(delayJob);
    }

    /**
     * 添加
     * @param request
     * @return
     */
    @RequestMapping(value = "add",method = RequestMethod.POST)
    public String addDefJob(Job request) {
        DelayJob delayJob = jobService.addDefJob(request);
        return JSON.toJSONString(delayJob);
    }

    /**
     * 获取
     * @return
     */
    @RequestMapping(value = "pop",method = RequestMethod.GET)
    public String getProcessJob(String topic) {
        Job process = jobService.getProcessJob(topic);
        return JSON.toJSONString(process);
    }
    /**
     * 完成一个执行的任务
     * @param jobId
     * @return
     */
    @RequestMapping(value = "finish",method = RequestMethod.DELETE)
    public String finishJob(Long jobId) {
        jobService.finishJob(jobId);
        return "success";
    }

    @RequestMapping(value = "delete",method = RequestMethod.DELETE)
    public String deleteJob(Long jobId) {
        jobService.deleteJob(jobId);
        return "success";
    }


}
