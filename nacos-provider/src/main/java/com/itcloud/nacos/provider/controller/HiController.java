package com.itcloud.nacos.provider.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yangkun
 * @date 2021-03-14
 */
@RestController
@RefreshScope //nacos-config的热加载
public class HiController {

    @Value("${username:lily}")
    String username;

    @GetMapping("/hi")
    public String hi(@RequestParam(value = "name",defaultValue = "forzp",required = false) String name){
        return "hi " + name;
    }

    @RequestMapping("/username") //测试nacos服务配置
    public String get(){
      return username;
    }

//    value：资源名称，必需项（不能为空）
//    entryType：entry 类型，可选项（默认为 EntryType.OUT）
//    blockHandler / blockHandlerClass: blockHandler 对应处理 BlockException 的函数名称，可选项
//    fallback：fallback 函数名称，可选项，用于在抛出异常的时候提供 fallback 处理逻辑。
    @GetMapping("/sentinel")
    @SentinelResource(value = "sentinel")
    public String sentinel(@RequestParam(value = "name",defaultValue = "forezp",required = false) String name){
      return "sentinel " +name;
    }

}
