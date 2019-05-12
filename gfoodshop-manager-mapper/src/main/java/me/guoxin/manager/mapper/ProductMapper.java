package me.guoxin.manager.mapper;

import me.guoxin.pojo.GfsOrder;
import me.guoxin.pojo.GfsProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

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

    /**
     * 删除商品
     * @param ids 商品id列表
     */
    void delete(List<Long> ids);

    List<GfsProduct> selectByIds(@Param("list")Set<Long> resultIds);
}
