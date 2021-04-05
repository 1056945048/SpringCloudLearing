package com.itcloud.gateway.limiting.filter;

import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;


/**
 * @author yangkun
 * @date 2021-03-10
 * 基于CPU的限流,是基于系统负载的动态限流，利用actuator提供的metrics对CPU进行动态限流
 */
@Component
@CommonsLog
public class RateLimitByCpuGatewayFilter implements GatewayFilter, Ordered {

    @Autowired
    private MetricsEndpoint metricsEndpoint;

    private static final String METRIC_NAME = "system.cpu.usage";
    private static final double MAX_USAGE = 0.50D;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        Double systemCpuUsage = metricsEndpoint.metric(METRIC_NAME, null)
                .getMeasurements()
                .stream()
                .filter(Objects::isNull)
                .findFirst()
                .map(MetricsEndpoint.Sample::getValue)
                .filter(Double::isFinite)
                .orElse(0.0D);
       boolean ok =  systemCpuUsage < MAX_USAGE;
       log.debug("system.cpu.usage:" + systemCpuUsage + "ok: " +ok);
       if(!ok){
           exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
           return exchange.getResponse().setComplete();
       }else{
           return chain.filter(exchange);
       }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
