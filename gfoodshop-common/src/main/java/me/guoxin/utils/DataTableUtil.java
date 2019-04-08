package me.guoxin.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import me.guoxin.pojo.DataTableDTO;
import me.guoxin.pojo.Order;
import me.guoxin.pojo.Page;
import me.guoxin.pojo.UserColumn;

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
        JSONObject orderData = (JSONObject) orderDatas.get(0);
        Order order = orderData.toJavaObject(Order.class);

        dataTableDTO.setSearch(search);
        dataTableDTO.setPage(page);
        dataTableDTO.setOrder(order);
        return dataTableDTO;
    }

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
}
