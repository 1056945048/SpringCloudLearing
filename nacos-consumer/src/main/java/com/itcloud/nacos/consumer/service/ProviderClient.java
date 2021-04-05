package com.itcloud.nacos.consumer.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author yangkun
 * @date 2021-03-14
 */
@FeignClient("nacos-provider")
public interface ProviderClient {

    @GetMapping("/hi")
    String hi(@RequestParam(value = "name",defaultValue = "forezp",required = false) String name);

    @GetMapping("/sentinel")
    String sentinel(@RequestParam(value = "name",defaultValue = "forezp",required = false) String name);
}
