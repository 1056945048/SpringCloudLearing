package com.itcloud.gateway.filter.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

/**
 * @author yangkun
 * @date 2021-03-09
 * 过滤器工厂的顶级接口是GatewayFilterFactory，有2个两个较接近具体实现的抽象类，
 * 分别为AbstractGatewayFilterFactory和
 * AbstractNameValueGatewayFilterFactory，
 * 这2个类前者接收一个参数，比如它的实现类RedirectToGatewayFilterFactory；
 * 后者接收2个参数，比如它的实现类AddRequestHeaderGatewayFilterFactory类。
 * 现在需要将请求的日志打印出来，需要使用一个参数，
 * 这时可以参照RedirectToGatewayFilterFactory的写法。
 */
@Component //自定义过滤器工厂，通过FilterFactory的前缀名RequestTime进行设置
public class RequestTimeGatewayFilterFactory  extends AbstractGatewayFilterFactory<RequestTimeGatewayFilterFactory.Config> {
    private static final Logger log = LoggerFactory.getLogger(RequestTimeGatewayFilterFactory.class);
    private static final String REQUEST_TIME_BEGIN = "requestTimeBegin";
    private static final String key = "withParams";
    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList(key);
    }
    public RequestTimeGatewayFilterFactory () {
        super(Config.class); //否则会报ClassCastException
    }


    //打印请求日志功能
    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            exchange.getAttributes().put(REQUEST_TIME_BEGIN,System.currentTimeMillis());
            return chain.filter(exchange).then(
                    Mono.fromRunnable(() -> {
                        Long startTime = exchange.getAttribute(REQUEST_TIME_BEGIN);
                        if(startTime != null) {
                            StringBuilder sb = new StringBuilder(exchange.getRequest().getURI().getRawPath())
                                          .append(":")
                                          .append(System.currentTimeMillis() - startTime)
                                          .append("ms");
                            if(config.isWithParams()) { //这里的值是通过配置文件中的RequestTime的值来改变的
                                sb.append(" params:").append(exchange.getRequest().getQueryParams());
                            }
                            log.info(sb.toString());
                        }
                    })
            );
        });
    }



    public static class Config {
        //用来接收服务参数，set方法可以控制是否开启，
        private boolean withParams;

        public boolean isWithParams() {
            return withParams;
        }
        public void setWithParams(boolean withParams) {
              this.withParams = withParams;
        }
    }
}
