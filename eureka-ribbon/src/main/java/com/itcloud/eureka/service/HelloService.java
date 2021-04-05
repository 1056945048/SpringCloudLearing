package com.itcloud.eureka.service;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author yangkun
 * @date 2021-03-06
 */
@Service
public class HelloService {
    @Autowired
    RestTemplate restTemplate;

    //该注解对该方法创建了熔断器的功能并指定熔断方法
    @HystrixCommand(fallbackMethod = "hiError")
    public String hiService(String name) {
      return restTemplate.getForObject("http://SERVICE-HI/hi?name="+name,String.class);
    }

    public String hiError(String name) {
        return "hi " +name +" sorry,error!";
    }
}
