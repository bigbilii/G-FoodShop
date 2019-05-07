package me.guoxin.manager.service;

import me.guoxin.dto.DataTableViewPageDTO;
import me.guoxin.pojo.DataTableDTO;
import me.guoxin.pojo.GfsCategories;
import me.guoxin.pojo.GfsStore;

import java.util.List;

public interface StoreService {

  
    /**
     * 新增商家信息
     *
     * @param gfsStore
     */
    void insertStore(GfsStore gfsStore);

    /**
     * 查询商家信息
     * 不包含产品信息
     * 以datatable方式存储
     *
     * @param dataTableDTO
     * @return
     */
    DataTableViewPageDTO<GfsStore> listStore(DataTableDTO dataTableDTO);

    /**
     * 修改商家信息
     *
     * @param gfsStore
     */
    void updateStore(GfsStore gfsStore);

    /**
     * 删除商家信息
     * @param ids
     */
    void deleteStore(List<Long> ids);
}
