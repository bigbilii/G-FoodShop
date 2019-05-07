package me.guoxin.manager.controller;

import me.guoxin.manager.service.RecommendService;
import me.guoxin.pojo.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class RecommendController {

    @Resource
    RecommendService recommendService;

    @PostMapping(value = "/makeRecommend")
    public Result makeRecommend() {
        recommendService.makeRecommend();
        return null;
    }

}
