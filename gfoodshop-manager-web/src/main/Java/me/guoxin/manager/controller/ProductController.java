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
    @GetMapping(value = "/product/list")
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
     * 查询商品信息
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
}
