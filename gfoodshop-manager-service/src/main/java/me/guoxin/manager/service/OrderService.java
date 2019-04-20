package me.guoxin.manager.service;

import me.guoxin.dto.DataTableViewPageDTO;
import me.guoxin.pojo.DataTableDTO;
import me.guoxin.pojo.GfsOrder;

import java.util.List;

public interface OrderService {

    /**
     * 用户插入
     * 订单数据
     * @param id
     * @param order
     */
    void insert(Long id, GfsOrder order);

    /**
     * 获取订单信息
     * @param id 用户id
     * @param dataTableDTO
     * @return
     */
    DataTableViewPageDTO<GfsOrder> getOrderListByUserId(Long id, DataTableDTO dataTableDTO);

    /**
     * 获取订单信息
     * @param dataTableDTO
     * @return
     */
    DataTableViewPageDTO<GfsOrder> getOrderList(DataTableDTO dataTableDTO);

    /**
     * 删除订单信息
     * @param ids 需要删除订单id列表
     */
    void deleteOrder(List<Long> ids);

    /**
     * 修改订单信息
     * 关闭订单
     * @param id
     */
    void orderClose(Long id);

    /**
     * 获取订单信息
     * @param id
     * @return
     */
    GfsOrder getOrder(Long id);
}
