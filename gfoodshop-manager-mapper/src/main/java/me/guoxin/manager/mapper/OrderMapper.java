package me.guoxin.manager.mapper;

import me.guoxin.pojo.GfsOrder;
import me.guoxin.pojo.GfsProductOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper {
    /**
     * 新增订单信息
     * @param order 订单信息
     * @return
     */
    int insertOrder(GfsOrder order) ;

    /**
     * 新增订单信息——商品信息
     * @param productOrders 商品信息
     * @return
     */
    int insertOrderProduct(List<GfsProductOrder> productOrders);

    /**
     * 查询订单信息+商品信息
     * @param id 用户id
     * @return
     */
    List<GfsOrder> selectByUserId(@Param("id") Long id);

    /**
     * 查询订单信息
     * @param id 订单id
     * @return
     */
    List<GfsOrder> selectByIdWithoutProduct(@Param("id") Long id);

    /**
     * 查询订单信息
     * @param id 用户id
     * @return
     */
    List<GfsOrder> selectByUserIdWithoutProduct(@Param("id")Long id);

    /**
     * 查询所有订单信息
     * @param search 查询信息
     * @return
     */
    List<GfsOrder> select(@Param("search")String search);

    /**
     * 删除订单信息
     * @param ids 订单id
     */
    void deleteOrders(List<Long> ids);

    /**
     * 删除订单信息-商品信息
     * @param ids 订单id
     */
    void deleteOrdersProducts(List<Long> ids);

    /**
     * 修改订单信息
     * @param order 订单信息
     * @return
     */
    int updateOrder(GfsOrder order);

    /**
     * 查询订单信息
     * @param id 订单id
     * @return
     */
    List<GfsOrder> selectById(Long id);
}
