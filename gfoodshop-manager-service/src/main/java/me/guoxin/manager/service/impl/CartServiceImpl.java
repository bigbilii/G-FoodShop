package me.guoxin.manager.service.impl;

import me.guoxin.manager.mapper.CartMapper;
import me.guoxin.manager.mapper.UserMapper;
import me.guoxin.manager.service.CartService;
import me.guoxin.pojo.GfsCart;
import me.guoxin.pojo.GfsProductCart;
import me.guoxin.pojo.GfsUser;
import me.guoxin.pojo.IException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Resource
    CartMapper cartMapper;
    @Resource
    UserMapper userMapper;

    @Override
    @Transactional
    public void insert(Long id, List<GfsProductCart> gfsProductCarts) {
        // 获取当前用户
        List<GfsUser> users = new ArrayList<>();
        try {
            users = userMapper.selectById(id);
        } catch (Exception e) {
            throw new IException("服务器错误");
        }
        if (users.isEmpty()) {
            throw new IException("当前用户不存在，请刷新页面重试");
        }
        GfsUser gfsUser = users.get(0);

        List<GfsCart> carts = new ArrayList<>();
        GfsCart gfsCart;
        try {
            carts = cartMapper.selectByUserId(id);
        } catch (Exception e) {
            throw new IException("服务器错误");
        }

        if (carts.isEmpty()) {
            gfsCart = new GfsCart();
            gfsCart.setUser(gfsUser);
            Date now = new Date();
            gfsCart.setCreateTime(now);
            gfsCart.setUpdateTime(now);
            gfsCart.setStatus(GfsCart._1);
            Long cid = cartMapper.insertCart(gfsCart);
            if (cid < 0) {
                throw new IException("操作失败！");
            }
        } else {
            gfsCart = carts.get(0);
            cartMapper.deleteCartProduct(gfsCart.getId());
        }

        for (GfsProductCart productCart : gfsProductCarts) {
            productCart.setCart(gfsCart);
        }

        if (cartMapper.insertCartProduct(gfsProductCarts) != gfsProductCarts.size()) {
            throw new IException("操作失败！");
        }

    }

    @Override
    public GfsCart selectByUserId(Long id) {
        // 获取当前用户
        List<GfsUser> users = new ArrayList<>();
        try {
            users = userMapper.selectById(id);
        } catch (Exception e) {
            throw new IException("服务器错误");
        }
        if (users.isEmpty()) {
            throw new IException("当前用户不存在，请刷新页面重试");
        }
        GfsUser gfsUser = users.get(0);

        List<GfsCart> carts = new ArrayList<>();
        try {
            carts = cartMapper.selectByUserId(id);
        } catch (Exception e) {
            throw new IException("服务器错误");
        }
        GfsCart cart;
        if (carts == null || carts.size() == 0) {
            cart = new GfsCart();
            cart.setProducts(new ArrayList<>());
        } else {
            cart = carts.get(0);
            cart.makeAllSum();
            cart.getUser().maskPasswordInfo();
        }
        return cart;
    }
}
