package me.guoxin.manager.mapper;

import me.guoxin.pojo.GfsCategories;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CategoriesMapper {

    /**
     * 查询分类信息
     * @return
     */
    List<GfsCategories> selectCategories();

    /**
     * 查询分类信息
     * 不包括其产品信息
     * @param id 分类id
     * @return
     */
    List<GfsCategories> selectCategoriesWithoutProductById(@Param("id") Long id);

    /**
     * 查询分类信息
     * 不包括其产品信息
     * @return
     */
    List<GfsCategories> selectCategoriesListWithoutProducts();
}
