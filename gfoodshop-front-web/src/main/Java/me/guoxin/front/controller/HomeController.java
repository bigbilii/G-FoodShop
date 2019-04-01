package me.guoxin.front.controller;

import me.guoxin.manager.service.CategoriesService;
import me.guoxin.pojo.GfsCategories;
import me.guoxin.pojo.Result;
import me.guoxin.utils.ResultUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class HomeController {
    @Resource
    private CategoriesService categoriesService;


    @GetMapping(value = "/homeFood/list")
    public Result getHomeFoodList() {
        List<GfsCategories> list = categoriesService.getAllFoodCategoriesWithFood();
        return new ResultUtil<>().setData(list);
    }
}
