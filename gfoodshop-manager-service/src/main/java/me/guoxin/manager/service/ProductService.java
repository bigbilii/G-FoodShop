package me.guoxin.manager.service;

import me.guoxin.dto.DataTableViewPageDTO;
import me.guoxin.pojo.DataTableDTO;
import me.guoxin.pojo.GfsOrder;
import me.guoxin.pojo.GfsProduct;

import java.util.List;

public interface ProductService {

    /**
     * 查询产品列表
     *
     * @param dataTableDTO
     * @return
     */
    DataTableViewPageDTO<GfsProduct> getProductList(DataTableDTO dataTableDTO);

    /**
     * 新增产品
     *
     * @param gfsProduct
     */
    void insertProduct(GfsProduct gfsProduct);

    /**
     * 修改商品
     *
     * @param gfsProduct
     */
    void updateProduct(GfsProduct gfsProduct);

    void disable(Long id);

    void restore(Long id);

    void deleteProduct(List<Long> ids);
}
