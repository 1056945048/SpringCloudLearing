package com.itcloud.redis.application.controller;

import com.itcloud.redis.application.model.Rank;
import com.itcloud.redis.application.repo.RankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * @author yangkun
 * @date 2021-03-20
 */
@RestController
@RequestMapping("/rank")
public class RankController {
    @Autowired
    RankRepository rankRepository;

    @PostMapping
    public Rank save(@RequestBody Rank rank){
        rankRepository.save(rank);
        return rank;
    }

    @CrossOrigin
    @GetMapping
    public Set<Rank> list(){
        return rankRepository.findAll();
    }
}
