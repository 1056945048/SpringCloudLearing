package com.itcloud.delay.queue.controller;

import com.alibaba.fastjson.JSON;
import com.itcloud.delay.queue.entity.User;
import com.itcloud.delay.queue.producer.DelaySender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yangkun
 * @date 2021-03-29
 */
@RestController
public class MessageController {

    @Autowired
    DelaySender delaySender;

    @GetMapping("/delay/send")
    public String delaySend() {
        User user = new User();
        user.setId("1");
        user.setName("张三");
        user.setAge("25");

        delaySender.send(user);
        return JSON.toJSONString(user);
    }

    //我们在DelaySend中设置的消息的超时时间为time参数ms,而在DelayConfig中设置的DelayQueue队列超时时间为4000ms，
    //注意：rabbitmq会采用比较小的值作为延迟时间。
    @GetMapping("/delay/send/{time}")
    public String delayTimeSend(@PathVariable long time) {
        User user = new User("2","Direct2","200",0);
        delaySender.send2(user,time);
        return JSON.toJSONString(user);
    }
}
