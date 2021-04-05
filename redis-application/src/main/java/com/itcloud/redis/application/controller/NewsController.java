package com.itcloud.redis.application.controller;

import com.itcloud.redis.application.model.News;
import com.itcloud.redis.application.repo.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author yangkun
 * @date 2021-03-20
 */
@RestController
@RequestMapping("/news")
public class NewsController {
    @Autowired
    private NewsRepository newsRepository;

    @PostMapping("/save")
    public News save(@RequestBody News news) {
        newsRepository.save(news);
        return news;
    }

    @CrossOrigin
    @GetMapping
    public List<News> list() {
        return newsRepository.list();
    }
}
