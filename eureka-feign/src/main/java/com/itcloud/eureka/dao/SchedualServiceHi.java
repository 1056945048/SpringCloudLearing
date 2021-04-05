package com.itcloud.eureka.dao;

import com.itcloud.eureka.hystrix.SchedualServiceHystirx;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author yangkun
 * @date 2021-03-06
 */
//Feign是自带断路器的，在D版本的Spring Cloud中，它默认关闭，我们只需要在配置文件中打开它
@FeignClient(value = "service-hi",fallback = SchedualServiceHystirx.class)
public interface SchedualServiceHi {

    @RequestMapping(value = "/hi",method = RequestMethod.GET)
    String sayHiFromClientOne(@RequestParam(value = "name") String name);

}
