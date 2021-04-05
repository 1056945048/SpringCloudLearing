package com.itcloud.gateway.limiting;

import com.itcloud.gateway.limiting.resolver.HostAddrKeyResolver;
import com.itcloud.gateway.limiting.resolver.RemoteAddrKeyResolver;
import com.itcloud.gateway.limiting.resolver.UriKeyResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

@SpringBootApplication
@EnableEurekaClient
public class GatewayLimitingApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayLimitingApplication.class, args);
    }
    @Bean
    public UriKeyResolver uriKeyResolver() {
        return new UriKeyResolver();
    }

    @Bean
    public HostAddrKeyResolver hostAddrKeyResolver() {
        return new HostAddrKeyResolver();
    }

    @Bean //根据用户的维度去限流
    KeyResolver userKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getQueryParams().getFirst("user"));
    }
    @Bean(name = RemoteAddrKeyResolver.BEAN_NAME)
    public RemoteAddrKeyResolver remoteAddrKeyResolver(){
        return new RemoteAddrKeyResolver();
    }
}
