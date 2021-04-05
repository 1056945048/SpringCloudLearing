package com.itcloud.zipkin.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
public class ZipkinServiceHiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZipkinServiceHiApplication.class, args);
    }

    private static final Logger log = LoggerFactory.getLogger(ZipkinServiceHiApplication.class.getName());

    @Autowired
    private RestTemplate restTemplate;
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @RequestMapping("/hi")
    public String callHome() {
        log.info("call trace zipkin-service-hi");
        return restTemplate.getForObject("http://localhost:8989/miya",String.class);
    }

    @RequestMapping("/info")
    public String info() {
        log.info("call trace zipkin-service-hi");
        return "i am zipkin-service-hi";
    }



}
