package com.itcloud.delay.queue.controller;

import com.alibaba.fastjson.JSON;
import com.itcloud.delay.queue.entity.User;
import com.itcloud.delay.queue.producer.RetrySender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yangkun
 * @date 2021-03-29
 */
@RestController
@RequestMapping("/retry")
public class RetryController {
    @Autowired
    RetrySender retrySender;

    @GetMapping(value = "send")
    public String sendMessage () {
        User user = new User("3","Direct3","200",0);
        retrySender.send(user);
        return JSON.toJSONString(user);
    }

    @RequestMapping(value = "send2",method = RequestMethod.GET)
    public String sendMessage2() {
        User user = new User("1","Direct1","200",0);
        retrySender.send(user);
        return JSON.toJSONString(user);
    }
}
