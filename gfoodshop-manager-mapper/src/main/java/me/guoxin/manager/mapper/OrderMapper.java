package me.guoxin.manager.mapper;

import me.guoxin.pojo.GfsOrder;
import me.guoxin.pojo.GfsProductOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper {
    int insertOrder(GfsOrder order) ;

    int insertOrderProduct(List<GfsProductOrder> productOrders);

    List<GfsOrder> selectByUserId(@Param("id") Long id);

    List<GfsOrder> selectByUserIdWithoutProduct(@Param("id")Long id);
}
