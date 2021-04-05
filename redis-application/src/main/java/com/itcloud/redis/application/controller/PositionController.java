package com.itcloud.redis.application.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.itcloud.redis.application.model.Position;
import com.itcloud.redis.application.repo.HotAreaRepository;
import com.itcloud.redis.application.repo.PositionRepository;
import com.itcloud.redis.application.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.itcloud.redis.application.model.User;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yangkun
 * @date 2021-03-20
 */
@RestController
@RequestMapping("/position")
public class PositionController {
    @Autowired
    PositionRepository positionRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    HotAreaRepository hotAreaRepository;

    @GetMapping("/{id}")
    public Position getPosition(@PathVariable String id) throws UnsupportedEncodingException {
        String userStr = userRepository.findById(id);
        if (userStr == null || userStr.isEmpty()) {
             return new Position();
        }
        User user = JSON.parseObject(userStr,User.class);
        Object obj = positionRepository.position(user.getPosition());
        List<Object> result = (List<Object>)obj;
        if (result == null || result.size() == 0) {
            return new Position();
        } else {
            List<byte[]> rawResults = (List)result.get(1);
            List<String> provinces = new ArrayList<String>();
            for(byte[] raw : rawResults) {
                provinces.add(new String(raw, "UTF-8"));
            }
            return new Position("name",provinces.get(0));
        }
    }

    @GetMapping("/code/{id}")
    public Position getCode(@PathVariable String id) throws UnsupportedEncodingException {
        int red = 0;
        String userStr = userRepository.findById(id);
        if (userStr == null || userStr.isEmpty()) {
           return new Position(red);
        }
        User user = JSONObject.parseObject(userStr, User.class);
        Object obj = positionRepository.position(user.getPosition());
        List<Object> results = (List<Object>) obj;
        if (results == null || results.size() ==0) {
            return new Position(red);
        } else {
           List<byte[]> rawsResults = (List) results.get(1);
           for(byte[] raw : rawsResults) {
              if (hotAreaRepository.contains(new String(raw, "UTF-8"))) {
                  red = 1;
              }
           }
           return new Position(red);
        }
    }

}
