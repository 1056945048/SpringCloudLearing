package com.itcloud.gateway.limiting.resolver;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author yangkun
 * @date 2021-03-10
 * //使用ip作为限流键的KeyResolver,resolver包中的其他类都需要依赖gateway官网提供的
 * 这次的ip限流依赖gateway提供的RequestRateLimiterGatewayFilterFactory ，重要属性配置查看application.yml
 */
public class RemoteAddrKeyResolver implements KeyResolver {

    public static final String BEAN_NAME = "remoteAddrKeyResolver";

    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        System.out.println(exchange.getRequest().getRemoteAddress().getAddress().getHostAddress());
        return Mono.just(exchange.getRequest().getRemoteAddress().getAddress().getHostAddress());
    }
}
