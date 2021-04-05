package com.itcloud.redis.application.controller;


import com.itcloud.redis.application.model.HotArea;
import com.itcloud.redis.application.repo.HotAreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author yangkun
 * @date 2021-03-20
 */
@RestController
@RequestMapping("/area")
public class HotAreaController {
    @Autowired
    HotAreaRepository hotAreaRepository;

    @PostMapping
    public HotArea save(@RequestBody HotArea hotArea) {
        hotAreaRepository.save(hotArea);
        return hotArea;
    }
}
