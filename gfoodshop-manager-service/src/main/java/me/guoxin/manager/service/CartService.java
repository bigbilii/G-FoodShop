package me.guoxin.manager.service;

import me.guoxin.pojo.GfsCart;
import me.guoxin.pojo.GfsProductCart;

import java.util.List;

public interface CartService {
    void insert(Long id,List<GfsProductCart>gfsProductCarts);

    GfsCart selectByUserId(Long id);
}
