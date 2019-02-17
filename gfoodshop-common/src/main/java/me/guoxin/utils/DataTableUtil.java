package me.guoxin.utils;

import com.alibaba.fastjson.JSON;
import me.guoxin.pojo.DataTableDTO;
import me.guoxin.pojo.Page;

import java.util.Map;

public class DataTableUtil {

    public static DataTableDTO getDataTableDTO(String tbData) {
        DataTableDTO dataTableDTO = new DataTableDTO();
        Map data = (Map) JSON.parse(tbData);
        // 获取search字段
        Map searchData = (Map) JSON.parse(data.get("search").toString());
        String search = searchData.get("value").toString();
        // 获取start字段
        int start = Integer.valueOf(data.get("start").toString());
        // 获取length字段
        int length = Integer.valueOf(data.get("length").toString());
        Page page = new Page();
        page.setStart(start);
        page.setLength(length);
        dataTableDTO.setSearch(search);
        dataTableDTO.setPage(page);
        return dataTableDTO;
    }
}
