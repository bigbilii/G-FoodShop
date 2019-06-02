package me.guoxin.manager.controller;

import me.guoxin.manager.service.RecommendService;
import me.guoxin.pojo.GfsFrequent;
import me.guoxin.pojo.GfsRule;
import me.guoxin.pojo.Result;
import me.guoxin.utils.ResultUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class RecommendController {

    @Resource
    RecommendService recommendService;

    @PostMapping(value = "/makeRecommend")
    public Result makeRecommend() throws InterruptedException {
        recommendService.makeRecommend();
        Thread.sleep(10000);
        return new ResultUtil<>().setData(null, "更新规则成功");
    }

    @GetMapping(value = "/getFrequent")
    public Result getFrequent() {
        List<GfsFrequent> list = recommendService.getFrequent();
        return new ResultUtil<>().setData(list);
    }

    @GetMapping(value = "/getRules")
    public Result getRules() {
        List<GfsRule> list = recommendService.getRules();
        return new ResultUtil<>().setData(list);
    }

    @GetMapping(value = "/makeOrder")
    public void makeOrder() {
        recommendService.makeOrder();
    }

}
