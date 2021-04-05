package com.itcloud.eureka.hystrix;

import com.itcloud.eureka.dao.SchedualServiceHi;
import org.springframework.stereotype.Component;

/**
 * @author yangkun
 * @date 2021-03-06
 */
@Component
public class SchedualServiceHystirx implements SchedualServiceHi {
    @Override
    public String sayHiFromClientOne(String name) {
        return "sorry , error !" + name;
    }
}
