package me.guoxin.front.controller;

import me.guoxin.manager.service.CartService;
import me.guoxin.manager.service.RecommendService;
import me.guoxin.manager.service.UserService;
import me.guoxin.pojo.GfsCart;
import me.guoxin.pojo.GfsProduct;
import me.guoxin.pojo.GfsProductCart;
import me.guoxin.pojo.Result;
import me.guoxin.utils.ResultUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController()
public class CartController {
    @Resource
    private CartService cartService;
    @Resource
    private RecommendService recommendService;

    @PostMapping(value = "/{id}/cart/insert")
    public Result insert(@PathVariable("id") Long id, @RequestBody List<GfsProductCart> gfsProductCarts) {
        cartService.insert(id, gfsProductCarts);
        return new ResultUtil<>().setData(null);
    }

    @GetMapping(value = "/{id}/cart/list")
    public Result cartList(@PathVariable("id") Long id) {
        GfsCart cart = cartService.selectByUserId(id);
        return new ResultUtil<>().setData(cart);
    }

    @GetMapping(value = "/{id}/recommend")
    public Result recommend(@PathVariable("id") Long id) {
        List<GfsProduct> list = recommendService.getRecommend(id);
        return new ResultUtil<>().setData(list);
    }
}
