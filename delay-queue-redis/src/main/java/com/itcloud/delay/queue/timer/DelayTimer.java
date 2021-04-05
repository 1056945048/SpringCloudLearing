package com.itcloud.delay.queue.timer;

import com.itcloud.delay.queue.container.DelayQueue;
import com.itcloud.delay.queue.container.JobPool;
import com.itcloud.delay.queue.container.ReadyQueue;
import com.itcloud.delay.queue.handler.DelayJobHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author yangkun
 * @date 2021-03-30
 * 轮询DelayQueue中的bucket,检查是否放入ReadyQueue,而且是不断运行，
 * ApplicationListener 监听某个事件
 * 本文中的 ContextRefreshedEvent事件，表示当所有的事件都被初始化完成并成功装载后触发该事件
 * 实现ApplicationListener<ContextRefreshedEvent>接口可以收到监听动作，然后可以写自己的逻辑。
 *
 * spring包含四种内置事件
 */
@Component
public class DelayTimer implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    DelayQueue delayQueue;

    @Autowired
    JobPool jobPool;

    @Autowired
    ReadyQueue readyQueue;

    @Value("${thread.size}")
    int num;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        //1.创建线程池
        ExecutorService executorService = new ThreadPoolExecutor(num,
                num,0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());

        //2.使用线程池添加执行任务，对bucket进行扫描
        for(int i=0;i<num;i++) {
            executorService.execute(new DelayJobHandler(
                    jobPool,
                    delayQueue,
                    readyQueue,
                    i));
        }
    }
}
