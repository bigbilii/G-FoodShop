package me.guoxin.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import me.guoxin.pojo.*;

import java.util.List;

public class DataTableUtil {

    /**
     * 获取 DataTableDTO实例
     *
     * @param tbData tbData Json字符串
     * @return DataTableDTO实例
     */
    public static DataTableDTO getDataTableDTO(String tbData) {
        DataTableDTO dataTableDTO = new DataTableDTO();
        JSONObject data = (JSONObject) JSON.parse(tbData);
        // 获取search字段
        JSONObject searchData = (JSONObject) data.get("search");
        String search = searchData.get("value").toString();
        // 获取start字段
        int start = Integer.valueOf(data.get("start").toString());
        // 获取length字段
        int length = Integer.valueOf(data.get("length").toString());
        Page page = new Page();
        page.setStart(start);
        page.setLength(length);
        // 获取排序字段
        List orderDatas = (JSONArray) data.get("order");
        Order order = null;
        if (orderDatas != null && orderDatas.size() > 0) {
            JSONObject orderData = (JSONObject) orderDatas.get(0);
            order = orderData.toJavaObject(Order.class);
        }

        dataTableDTO.setSearch(search);
        dataTableDTO.setPage(page);
        dataTableDTO.setOrder(order);
        return dataTableDTO;
    }

    /**
     * 获取用户 DataTableDTO排序列表值
     * @param order
     * @return
     */
    public static String getUserDataTableOrderBy(Order order) {
        String pre = "u.";
        if (order == null) {
            return pre + UserColumn.ID.getColumn() + " " + "desc";
        }
        int column = order.getColumn();
        String dir = order.getDir();
        String columnName = UserColumn.getColunm(column);
        if (columnName == null || "".equalsIgnoreCase(columnName.trim())) {
            columnName = UserColumn.ID.getColumn();
        }
        return pre + columnName + " " + dir;
    }

    /**
     * 获取订单 DataTableDTO排序列表值
     * @param order
     * @return
     */
    public static String getOrderDataTableOrderBy(Order order) {
        String pre = "o.";
        if (order == null) {
            return pre + OrderColumn.ID.getColumn() + " " + "desc";
        }
        int column = order.getColumn();
        String dir = order.getDir();
        String columnName = OrderColumn.getColunm(column);
        if (columnName == null || "".equalsIgnoreCase(columnName.trim())) {
            columnName = OrderColumn.ID.getColumn();
        }
        return pre + columnName + " " + dir;
    }

    /**
     * 获取商品 DataTableDTO排序列表值
     * @param order
     * @return
     */
    public static String getProductDataTableOrderBy(Order order) {
        String pre = "p.";
        if (order == null) {
            return pre + ProductColumn.ID.getColumn() + " " + "desc";
        }
        int column = order.getColumn();
        String dir = order.getDir();
        String columnName = ProductColumn.getColunm(column);
        if (columnName == null || "".equalsIgnoreCase(columnName.trim())) {
            columnName = ProductColumn.ID.getColumn();
        }
        return pre + columnName + " " + dir;
    }
}
