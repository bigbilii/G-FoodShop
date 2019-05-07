package me.guoxin.manager.controller;

import me.guoxin.dto.DataTableViewPageDTO;
import me.guoxin.manager.service.CategoriesService;
import me.guoxin.manager.service.StoreService;
import me.guoxin.pojo.DataTableDTO;
import me.guoxin.pojo.GfsCategories;
import me.guoxin.pojo.GfsStore;
import me.guoxin.pojo.Result;
import me.guoxin.utils.DataTableUtil;
import me.guoxin.utils.ResultUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class StoreController {
    @Resource
    StoreService storeService;

    /**
     * 查询商家列表
     *
     * @return
     * @throws Exception
     */
    @RequiresPermissions("store:list")
    @GetMapping(value = "/store/list/tb")
    public Result listUsers(String tbData) {
        // 处理dataTable发送Json字符串参数
        DataTableDTO dataTableDTO = DataTableUtil.getDataTableDTO(tbData);
        DataTableViewPageDTO<GfsStore> list = storeService.listStore(dataTableDTO);
        return new ResultUtil<>().setData(list);
    }

    /**
     * 新增商家信息
     *
     * @param
     * @return
     */
    @RequiresPermissions("store:insert")
    @PostMapping(value = "/store/insert")
    public Result insert(@RequestBody GfsStore gfsStore) {
        storeService.insertStore(gfsStore);
        return new ResultUtil<>().setData(null, "新增商家成功");
    }

    /**
     * 修改商家信息
     *
     * @param
     * @return
     */
    @RequiresPermissions("store:update")
    @PutMapping(value = "/store/update")
    public Result update(@RequestBody GfsStore gfsStore) {
        storeService.updateStore(gfsStore);
        return new ResultUtil<>().setData(null, "修改商品成功");
    }
    /**
     * 删除商家
     *
     * @return
     * @throws Exception
     */
    @RequiresPermissions("store:delete")
    @DeleteMapping(value = "/store/delete")
    public Result deleteUser(@RequestBody List<Long> ids) {
        storeService.deleteStore(ids);
        return new ResultUtil<>().setData(null, "删除成功");
    }
}
