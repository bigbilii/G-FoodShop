package me.guoxin.manager.mapper;

import me.guoxin.pojo.GfsOrder;
import me.guoxin.pojo.GfsProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {
    /**
     * 查询商品列表
     *
     * @param search
     * @return
     */
    List<GfsProduct> select(@Param("search") String search);

    /**
     * 插入商品
     *
     * @param gfsProduct
     * @return
     */
    int insert(GfsProduct gfsProduct);

    /**
     * 查询商品
     * @param id 商品id
     * @return
     */
    List<GfsProduct> selectById(@Param("id") Long id);

    /**
     * 修改商品
     * @param gfsProduct
     * @return
     */
    int update(GfsProduct gfsProduct);
}
