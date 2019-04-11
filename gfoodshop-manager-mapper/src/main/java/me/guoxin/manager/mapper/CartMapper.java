package me.guoxin.manager.mapper;

import me.guoxin.pojo.GfsCart;
import me.guoxin.pojo.GfsProductCart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartMapper {

    List<GfsCart> selectByUserId(@Param("id")Long id);

    int insertCartProduct(List<GfsProductCart> gfsProductCarts);

    Long insertCart(GfsCart gfsCart);

    void deleteCartProduct(@Param("id")Long id);
}
