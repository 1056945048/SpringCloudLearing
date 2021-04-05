package com.itcloud.eureka.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.sun.org.apache.regexp.internal.RE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yangkun
 * @date 2021-03-06
 * 1.zuul中定义了四种不同生命周期的过滤器类型
 * - pre 路由之前
 * -routing 路由之时
 * -post 路由之后
 * -error 发送错误调用
 *
 * 2.filterOrder 过滤的顺序
 *
 * 3.shouldFilter 这里可以写过滤逻辑，是否过滤，本文为true 表示永远过滤
 *
 * 4.run 过滤器的具体逻辑，可以很复杂，包括查sql,nosql判断该请求是否有权限访问
 */
@Component
public class MyFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger(MyFilter.class);

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        log.info(String.format("%s >>> %s",request.getMethod(),request.getRequestURL().toString()));
        Object accessToken = request.getParameter("token");
        if(accessToken == null) {
            log.warn("token is empty");
            currentContext.setSendZuulResponse(false);
            currentContext.setResponseStatusCode(401);
            try{
              currentContext.getResponse().getWriter().write("token is empty");
            }catch (Exception e){}
            return null;
        }
        log.info("ok");
        return null;
    }
}
