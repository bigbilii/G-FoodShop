package me.guoxin.front.controller;

import me.guoxin.dto.DataTableViewPageDTO;
import me.guoxin.dto.OnlineUserDTO;
import me.guoxin.manager.service.OrderService;
import me.guoxin.pojo.DataTableDTO;
import me.guoxin.pojo.GfsOrder;
import me.guoxin.pojo.GfsProductCart;
import me.guoxin.pojo.Result;
import me.guoxin.utils.DataTableUtil;
import me.guoxin.utils.ResultUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class OrderController {

    @Resource
    OrderService orderService;

    @PostMapping(value = "/{id}/order/insert")
    public Result insert(@PathVariable("id") Long id, @RequestBody GfsOrder order) {
        orderService.insert(id,order);
        return new ResultUtil<>().setData(null);
    }

    @GetMapping(value = "/{id}/order/select")
    public Result select(@PathVariable("id") Long id, String tbData) {
        DataTableDTO dataTableDTO = DataTableUtil.getDataTableDTO(tbData);
        DataTableViewPageDTO<GfsOrder> list = orderService.getOrderListByUserId(id, dataTableDTO);
        return new ResultUtil<>().setData(list);
    }
}
