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
public class ZipkinServiceMiyaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZipkinServiceMiyaApplication.class, args);
    }
    private static final Logger log = LoggerFactory.getLogger(ZipkinServiceMiyaApplication.class.getName());

    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

    @RequestMapping("/miya")
    public String info() {
        log.info("info is being called");
        return restTemplate.getForObject("http://localhost:8988/info",String.class);
    }

    @RequestMapping("/hi")
    public String home() {
        log.info("zipkin-service-miya is being called");
        return " i am zipkin-service-miya,hi";
    }

}
