package me.guoxin.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import me.guoxin.dto.DataTableViewPageDTO;
import me.guoxin.dto.OnlineUserDTO;
import me.guoxin.manager.mapper.*;
import me.guoxin.manager.service.OrderService;
import me.guoxin.manager.utils.CommonUtils;
import me.guoxin.pojo.*;
import me.guoxin.utils.DataTableUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    UserMapper userMapper;
    @Resource
    AddressMapper addressMapper;
    @Resource
    OrderMapper orderMapper;
    @Resource
    CartMapper cartMapper;
    @Resource
    StoreMapper storeMapper;


    @Override
    @Transactional
    public void insert(Long id, GfsOrder order) {
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

        // 获取可送达餐厅
        List<GfsStore> stores = storeMapper.selectByCityId(order.getAddress().getCity().getId());
        if (stores == null || stores.size() <= 0) {
            throw new IException("当前送餐地址没有可送达的餐厅");
        }
        GfsStore store = CommonUtils.getCloseStore(stores, order.getAddress());

        order.setStore(store);
        order.setUser(gfsUser);
        order.setStatus(GfsOrder.STATUS_CREATE);
        order.setCreateTime(new Date());
        if (orderMapper.insertOrder(order) != 1) {
            throw new IException("操作失败！");
        }
        for (GfsProductOrder productOrder : order.getProductOrders()) {
            productOrder.setOrder(order);
        }
        if (orderMapper.insertOrderProduct(order.getProductOrders()) != order.getProductOrders().size()) {
            throw new IException("操作失败！");
        }
        cartMapper.deleteCartProductByUserId(gfsUser.getId());
    }

    @Override
    public DataTableViewPageDTO<GfsOrder> getOrderListByUserId(Long id, DataTableDTO dataTableDTO) {
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

        DataTableViewPageDTO<GfsOrder> dtoDataTableViewPageDTO = new DataTableViewPageDTO<>();
        int startIndex = dataTableDTO.getPage().getStart();
        int length = dataTableDTO.getPage().getLength();
        startIndex = startIndex / length + 1;
        String OrderBy = "o.create_time desc";
        PageHelper.startPage(startIndex, length, OrderBy);

        List<GfsOrder> orders = orderMapper.selectByUserIdWithoutProduct(id);
        for (GfsOrder order : orders) {
            if (order.getUser() != null)
                order.getUser().maskPasswordInfo();
            if (order.getAddress().getUser() != null)
                order.getAddress().getUser().maskPasswordInfo();
        }
        PageInfo<GfsOrder> pageInfo = new PageInfo<>(orders);
        long total = pageInfo.getTotal();
        dtoDataTableViewPageDTO.setData(orders);
        dtoDataTableViewPageDTO.setiTotalRecords(length);
        dtoDataTableViewPageDTO.setiTotalDisplayRecords(total);
        return dtoDataTableViewPageDTO;
    }

    @Override
    public DataTableViewPageDTO<GfsOrder> getOrderList(DataTableDTO dataTableDTO) {
        DataTableViewPageDTO<GfsOrder> dtoDataTableViewPageDTO = new DataTableViewPageDTO<>();
        int startIndex = dataTableDTO.getPage().getStart();
        int length = dataTableDTO.getPage().getLength();
        startIndex = startIndex / length + 1;
        String OrderBy = DataTableUtil.getOrderDataTableOrderBy(dataTableDTO.getOrder());
        PageHelper.startPage(startIndex, length, OrderBy);
        Map<String, Object> map = new HashMap<>();
        map.put("search", dataTableDTO.getSearch());
        List<GfsOrder> orders = orderMapper.select(map);
        for (GfsOrder order : orders) {
            if (order.getUser() != null)
                order.getUser().maskPasswordInfo();
            if (order.getAddress().getUser() != null)
                order.getAddress().getUser().maskPasswordInfo();
        }
        PageInfo<GfsOrder> pageInfo = new PageInfo<>(orders);
        long total = pageInfo.getTotal();
        dtoDataTableViewPageDTO.setData(orders);
        dtoDataTableViewPageDTO.setiTotalRecords(length);
        dtoDataTableViewPageDTO.setiTotalDisplayRecords(total);
        return dtoDataTableViewPageDTO;
    }

    @Override
    public DataTableViewPageDTO<GfsOrder> getOrderList(DataTableDTO dataTableDTO, Long id) {
        DataTableViewPageDTO<GfsOrder> dtoDataTableViewPageDTO = new DataTableViewPageDTO<>();
        int startIndex = dataTableDTO.getPage().getStart();
        int length = dataTableDTO.getPage().getLength();
        startIndex = startIndex / length + 1;
        String OrderBy = DataTableUtil.getOrderDataTableOrderBy(dataTableDTO.getOrder());
        PageHelper.startPage(startIndex, length, OrderBy);
        Map<String, Object> map = new HashMap<>();
        map.put("search", dataTableDTO.getSearch());
        map.put("storeId", id);
        map.put("status", dataTableDTO.getStatus());
        map.put("s", dataTableDTO.getS());
        map.put("e", dataTableDTO.getE());
        List<GfsOrder> orders = orderMapper.select(map);
        for (GfsOrder order : orders) {
            if (order.getUser() != null)
                order.getUser().maskPasswordInfo();
            if (order.getAddress().getUser() != null)
                order.getAddress().getUser().maskPasswordInfo();
        }
        PageInfo<GfsOrder> pageInfo = new PageInfo<>(orders);
        long total = pageInfo.getTotal();
        dtoDataTableViewPageDTO.setData(orders);
        dtoDataTableViewPageDTO.setiTotalRecords(length);
        dtoDataTableViewPageDTO.setiTotalDisplayRecords(total);
        return dtoDataTableViewPageDTO;
    }

    @Override
    @Transactional
    public void deleteOrder(List<Long> ids) {
        orderMapper.deleteOrdersProducts(ids);
        orderMapper.deleteOrders(ids);
    }

    @Override
    public void orderClose(Long id) {
        List<GfsOrder> list = orderMapper.selectByIdWithoutProduct(id);
        if (list == null || list.isEmpty()) {
            throw new IException("更新订单发生错误，请刷新重试！");
        }
        GfsOrder order = list.get(0);
        order.setStatus(GfsOrder.STATUS_CLOSE);
        if (orderMapper.updateOrder(order) != 1) {
            throw new IException("修改订单失败！");
        }
    }

    @Override
    public void orderAccept(Long id) {
        List<GfsOrder> list = orderMapper.selectByIdWithoutProduct(id);
        if (list == null || list.isEmpty()) {
            throw new IException("更新订单发生错误，请刷新重试！");
        }
        GfsOrder order = list.get(0);
        if (order.getStatus() == GfsOrder.STATUS_CREATE) {
            order.setStatus(GfsOrder.STATUS_MAKE);
        } else {
            throw new IException("订单当前状态不可接受");
        }
        if (orderMapper.updateOrder(order) != 1) {
            throw new IException("修改订单失败！");
        }
    }

    @Override
    public void orderSend(Long id) {
        List<GfsOrder> list = orderMapper.selectByIdWithoutProduct(id);
        if (list == null || list.isEmpty()) {
            throw new IException("更新订单发生错误，请刷新重试！");
        }
        GfsOrder order = list.get(0);
        if (order.getStatus() == GfsOrder.STATUS_MAKE) {
            order.setStatus(GfsOrder.STATUS_SEND);
            order.setSendTime(new Date());
        } else {
            throw new IException("订单当前状态不可配送");
        }
        if (orderMapper.updateOrder(order) != 1) {
            throw new IException("修改订单失败！");
        }
    }

    @Override
    public void orderDone(Long id) {
        List<GfsOrder> list = orderMapper.selectByIdWithoutProduct(id);
        if (list == null || list.isEmpty()) {
            throw new IException("更新订单发生错误，请刷新重试！");
        }
        GfsOrder order = list.get(0);
        if (order.getStatus() == GfsOrder.STATUS_SEND) {
            order.setStatus(GfsOrder.STATUS_ARRIVE);
            order.setArriveTime(new Date());
        } else {
            throw new IException("订单当前状态不可完成");
        }
        if (orderMapper.updateOrder(order) != 1) {
            throw new IException("修改订单失败！");
        }
    }

    @Override
    public GfsOrder getOrder(Long id) {
        List<GfsOrder> list = orderMapper.selectById(id);
        if (list == null || list.isEmpty()) {
            throw new IException("查询失败");
        }
        return list.get(0);
    }

    @Override
    public void cancel(Long id) {
        List<GfsOrder> list = orderMapper.selectById(id);
        if (list == null || list.isEmpty()) {
            throw new IException("服务器错误");
        }
        GfsOrder order = list.get(0);
        if (order.getStatus() == GfsOrder.STATUS_CREATE) {
            order.setStatus(GfsOrder.STATUS_CLOSE);
            orderMapper.updateOrder(order);
        } else {
            throw new IException("当前订单状态不可取消");
        }
    }
}