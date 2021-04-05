package com.itcloud.gateway.registry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class GatewayRegistryApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayRegistryApplication.class, args);
    }

}
