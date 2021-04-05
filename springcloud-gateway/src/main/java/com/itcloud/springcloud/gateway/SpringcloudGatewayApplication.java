package com.itcloud.springcloud.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@SpringBootApplication
@RestController
public class SpringcloudGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringcloudGatewayApplication.class, args);
    }

    @Bean
    public RouteLocator myRoute(RouteLocatorBuilder builder){
         return builder.routes().route(p -> p
                                .path("/get")  //所有的get请求都转发到http://httpbin.org:80
                                .filters(f -> f.addRequestHeader("Hello","World"))
                                .uri("http://httpbin.org:80")).build();
    }

    //spring cloud gateway 结合Hystrix使用
    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        String httpUri = "http://httpbin.org:80";
        return builder.routes().route(p -> p
                                         .path("/get")
                                         .filters(f -> f.addRequestHeader("hello","world"))
                                         .uri(httpUri))
                               .route(p -> p //当请求的host有 *.hystrix.com时,都会路由执行fallback方法
                                         .host("*.hystrix.com")
                                         .filters(f -> f
                                                 .hystrix(config -> config
                                                                .setName("mycmd")
                                                                .setFallbackUri("forward:/fallback")))
                                        .uri(httpUri))
                               .build();

    }
    @RequestMapping("/fallback")
    public Mono<String> fallback() {
        return Mono.just("fallback");
    }




}
