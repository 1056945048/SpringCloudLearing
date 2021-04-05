package com.itcloud.eureka.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerAdminApplication.class, args);
    }

}
