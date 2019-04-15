package me.guoxin.manager.service;

import me.guoxin.dto.DataTableViewPageDTO;
import me.guoxin.pojo.DataTableDTO;
import me.guoxin.pojo.GfsOrder;

public interface OrderService {

    void insert(Long id, GfsOrder order);

    DataTableViewPageDTO<GfsOrder> getOrderListByUserId(Long id, DataTableDTO dataTableDTO);
}
