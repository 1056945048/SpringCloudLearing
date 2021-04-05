package com.itcloud.gateway.limiting;

import com.itcloud.gateway.limiting.filter.RateLimitByCpuGatewayFilter;
import com.itcloud.gateway.limiting.filter.RateLimitByIpGatewayFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.time.Duration;

@SpringBootApplication
public class GatewayLimitingMyselfApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayLimitingMyselfApplication.class, args);
    }


    //将自定义的ip限流器注入Spring IOC容器中
    @Bean
    public RouteLocator createMySelfLimitFilter(RouteLocatorBuilder routeLocatorBuilder){
        RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();
        return routes.route( r -> r.path("/throttle/customer/**")
                                   .filters( f -> f.stripPrefix(2)
                                                   .filter(new RateLimitByIpGatewayFilter(10,1, Duration.ofSeconds(1))))
                                   .uri("http://httpbin.org:80/get")
                                   .order(0)
                                   .id("my_limit_filter")
                            ).build();

    }
    //将自定义CPU限流器配置在route中（动态限流）
    @Autowired
    private RateLimitByCpuGatewayFilter rateLimitByCpuGatewayFilter;

    @Bean
    public RouteLocator customerRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
        RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();
        return routes.route(r -> r.path("/throttle/customer/**")
                                 .filters( f -> f.stripPrefix(2)
                                                 .filter(rateLimitByCpuGatewayFilter))
                                 .uri("http://httpbin.org:80/get")
                                 .order(0)
                                 .id("my_cpu_filter")).build();
    }


}
