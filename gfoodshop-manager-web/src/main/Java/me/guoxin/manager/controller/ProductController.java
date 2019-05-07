package me.guoxin.manager.controller;

import me.guoxin.dto.DataTableViewPageDTO;
import me.guoxin.manager.service.ProductService;
import me.guoxin.pojo.DataTableDTO;
import me.guoxin.pojo.GfsOrder;
import me.guoxin.pojo.GfsProduct;
import me.guoxin.pojo.Result;
import me.guoxin.utils.DataTableUtil;
import me.guoxin.utils.ResultUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class ProductController {

    @Resource
    ProductService productService;

    /**
     * 查询商品信息
     *
     * @param tbData
     * @return
     */
    @RequiresPermissions("product:list")
    @GetMapping(value = "/product/list/tb")
    public Result select(String tbData) {
        DataTableDTO dataTableDTO = DataTableUtil.getDataTableDTO(tbData);
        DataTableViewPageDTO<GfsProduct> list = productService.getProductList(dataTableDTO);
        return new ResultUtil<>().setData(list);
    }

    /**
     * 查询商品信息
     *
     * @param
     * @return
     */
    @RequiresPermissions("product:insert")
    @PostMapping(value = "/product/insert")
    public Result insert(@RequestBody GfsProduct gfsProduct) {
        productService.insertProduct(gfsProduct);
        return new ResultUtil<>().setData(null, "新增商品成功");
    }

    /**
     * 修改商品信息
     *
     * @param
     * @return
     */
    @RequiresPermissions("product:update")
    @PutMapping(value = "/product/update")
    public Result update(@RequestBody GfsProduct gfsProduct) {
        productService.updateProduct(gfsProduct);
        return new ResultUtil<>().setData(null, "修改商品成功");
    }

    /**
     * 删除用户
     *
     * @return
     * @throws Exception
     */
    @RequiresPermissions("product:delete")
    @DeleteMapping(value = "/product/delete")
    public Result deleteUser(@RequestBody List<Long> ids) {
        productService.deleteProduct(ids);
        return new ResultUtil<>().setData(null, "删除成功");
    }

    /**
     * 下架商品
     *
     * @param id 商品id
     * @return
     * @throws Exception
     */
    @RequiresPermissions("product:update")
    @PutMapping(value = "/product/{id}/disable")
    public Result disable(@PathVariable Long id) {
        productService.disable(id);
        return new ResultUtil<Object>().setData(null, "修改成功");
    }

    /**
     * 回复商品
     *
     * @param id 商品id
     * @return
     * @throws Exception
     */
    @RequiresPermissions("product:update")
    @PutMapping(value = "/product/{id}/restore")
    public Result restore(@PathVariable Long id) {
        productService.restore(id);
        return new ResultUtil<Object>().setData(null, "修改成功");
    }
}
