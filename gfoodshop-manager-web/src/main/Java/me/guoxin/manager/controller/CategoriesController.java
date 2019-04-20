package me.guoxin.manager.controller;

import me.guoxin.dto.DataTableViewPageDTO;
import me.guoxin.manager.service.CategoriesService;
import me.guoxin.pojo.DataTableDTO;
import me.guoxin.pojo.GfsCategories;
import me.guoxin.pojo.GfsProduct;
import me.guoxin.pojo.Result;
import me.guoxin.utils.DataTableUtil;
import me.guoxin.utils.ResultUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class CategoriesController {
    @Resource
    CategoriesService categoriesService;

    /**
     * 查询分类信息
     * @return
     */
    @RequiresPermissions("categories:list")
    @GetMapping(value = "/categories/list")
    public Result select() {
        List<GfsCategories> list = categoriesService.getCategoriesListWithoutProduct();
        return new ResultUtil<>().setData(list);
    }
}
