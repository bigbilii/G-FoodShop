package me.guoxin.manager.controller;

import me.guoxin.dto.DataTableViewPageDTO;
import me.guoxin.manager.service.CategoriesService;
import me.guoxin.pojo.*;
import me.guoxin.utils.DataTableUtil;
import me.guoxin.utils.ResultUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class CategoriesController {
    @Resource
    CategoriesService categoriesService;

    /**
     * 查询分类信息
     *
     * @return
     */
    @RequiresPermissions("categories:list")
    @GetMapping(value = "/categories/list")
    public Result select() {
        List<GfsCategories> list = categoriesService.getCategoriesListWithoutProduct();
        return new ResultUtil<>().setData(list);
    }

    /**
     * 查询分类列表
     *
     * @return
     * @throws Exception
     */
    @RequiresPermissions("categories:list")
    @GetMapping(value = "/categories/list/tb")
    public Result listUsers(String tbData) {
        // 处理dataTable发送Json字符串参数
        DataTableDTO dataTableDTO = DataTableUtil.getDataTableDTO(tbData);
        DataTableViewPageDTO<GfsCategories> list = categoriesService.listCategories(dataTableDTO);
        return new ResultUtil<>().setData(list);
    }

    /**
     * 新增分类信息
     *
     * @param
     * @return
     */
    @RequiresPermissions("categories:insert")
    @PostMapping(value = "/categories/insert")
    public Result insert(@RequestBody GfsCategories gfsCategories) {
        categoriesService.insertCategories(gfsCategories);
        return new ResultUtil<>().setData(null, "新增分类成功");
    }

    /**
     * 修改分类信息
     *
     * @param
     * @return
     */
    @RequiresPermissions("categories:update")
    @PutMapping(value = "/categories/update")
    public Result update(@RequestBody GfsCategories gfsCategories) {
        categoriesService.updateCategories(gfsCategories);
        return new ResultUtil<>().setData(null, "修改商品成功");
    }
    /**
     * 删除分类
     *
     * @return
     * @throws Exception
     */
    @RequiresPermissions("categories:delete")
    @DeleteMapping(value = "/categories/delete")
    public Result deleteUser(@RequestBody List<Long> ids) {
        categoriesService.deleteCategories(ids);
        return new ResultUtil<>().setData(null, "删除成功");
    }
}
