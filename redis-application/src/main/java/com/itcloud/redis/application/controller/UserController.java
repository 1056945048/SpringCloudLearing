package com.itcloud.redis.application.controller;

import com.alibaba.fastjson.JSON;
import com.itcloud.redis.application.repo.UserRepository;
import com.itcloud.redis.application.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author yangkun
 * @date 2021-03-20
 */
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @PostMapping
    public User save(@RequestBody User user){
        userRepository.save(user);
        return user;
    }

    @GetMapping
    public List<String> list() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable String id) {
        return JSON.parseObject(userRepository.findById(id),User.class);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable String id) {
        userRepository.delete(id);
        return id;
    }
}
