package com.itcloud.nacos.consumer.controller;

import com.itcloud.nacos.consumer.service.ProviderClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author yangkun
 * @date 2021-03-14
 */
@RestController
public class ConsumerController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ProviderClient providerClient;

    @GetMapping("/hi-restTemplate")
    public String hiRestTemplate(){
       return restTemplate.getForObject("http://nacos-provider/hi?name=restTemplate",String.class);
    }

    @GetMapping("/hi-openFeign")
    public String hiOpenFeign(){
        return providerClient.hi("openFeign");
    }

    @GetMapping("/hi-sentinel")
    public String hiSentinel() {
        return providerClient.sentinel("sentinel");
    }


}
