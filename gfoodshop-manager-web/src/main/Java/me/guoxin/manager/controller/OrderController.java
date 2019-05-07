package me.guoxin.manager.controller;

import me.guoxin.dto.DataTableViewPageDTO;
import me.guoxin.manager.service.OrderService;
import me.guoxin.pojo.DataTableDTO;
import me.guoxin.pojo.GfsOrder;
import me.guoxin.pojo.Result;
import me.guoxin.utils.DataTableUtil;
import me.guoxin.utils.ResultUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class OrderController {
    @Resource
    OrderService orderService;


    /**
     * 查询订单信息
     *
     * @param tbData
     * @return
     */
    @RequiresPermissions("order:list")
    @GetMapping(value = "/order/list/tb")
    public Result select(String tbData) {
        DataTableDTO dataTableDTO = DataTableUtil.getDataTableDTO(tbData);
        DataTableViewPageDTO<GfsOrder> list = orderService.getOrderList(dataTableDTO);
        return new ResultUtil<>().setData(list);
    }

    /**
     * 查询订单信息
     *
     * @param tbData
     * @return
     */
    @RequiresPermissions("order:list")
    @GetMapping(value = "/order/list/tb/{id}")
    public Result select(String tbData, @PathVariable Long id) {
        DataTableDTO dataTableDTO = DataTableUtil.getDataTableDTO(tbData);
        DataTableViewPageDTO<GfsOrder> list = orderService.getOrderList(dataTableDTO, id);
        return new ResultUtil<>().setData(list);
    }

    /**
     * 关闭订单
     *
     * @param
     * @return
     */
    @RequiresPermissions("order:update")
    @PutMapping(value = "/order/{id}/close")
    public Result close(@PathVariable Long id) {
        orderService.orderClose(id);
        return new ResultUtil<>().setData(null, "修改成功");
    }

    /**
     * 关闭订单
     *
     * @param
     * @return
     */
    @RequiresPermissions("order:update")
    @PutMapping(value = "/order/{id}/accept")
    public Result accept(@PathVariable Long id) {
        orderService.orderAccept(id);
        return new ResultUtil<>().setData(null, "修改成功");
    }

    /**
     * 关闭订单
     *
     * @param
     * @return
     */
    @RequiresPermissions("order:update")
    @PutMapping(value = "/order/{id}/send")
    public Result send(@PathVariable Long id) {
        orderService.orderSend(id);
        return new ResultUtil<>().setData(null, "修改成功");
    }

    /**
     * 关闭订单
     *
     * @param
     * @return
     */
    @RequiresPermissions("order:update")
    @PutMapping(value = "/order/{id}/done")
    public Result done(@PathVariable Long id) {
        orderService.orderDone(id);
        return new ResultUtil<>().setData(null, "修改成功");
    }


    /**
     * 删除订单信息
     *
     * @return
     * @throws Exception
     */
    @RequiresPermissions("order:delete")
    @DeleteMapping(value = "/order/delete")
    public Result delete(@RequestBody List<Long> ids) {
        orderService.deleteOrder(ids);
        return new ResultUtil<>().setData(null, "删除成功");
    }

    /**
     * 查询商品信息
     *
     * @return
     */
    @GetMapping(value = "/order/{id}")
    public Result select(@PathVariable("id") Long id) {
        GfsOrder gfsOrder = orderService.getOrder(id);
        return new ResultUtil<>().setData(gfsOrder);
    }
}
