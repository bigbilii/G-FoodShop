package me.guoxin.manager.mapper;

import me.guoxin.pojo.GfsCategories;
import me.guoxin.pojo.GfsUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CategoriesMapper {

    /**
     * 查询分类信息
     *
     * @return
     */
    List<GfsCategories> selectCategories();

    /**
     * 查询分类信息
     * 不包括其产品信息
     *
     * @param id 分类id
     * @return
     */
    List<GfsCategories> selectCategoriesWithoutProductById(@Param("id") Long id);

    /**
     * 查询分类信息
     * 不包括其产品信息
     *
     * @return
     */
    List<GfsCategories> selectCategoriesListWithoutProducts();

    /**
     * 新增分类信息
     *
     * @param gfsCategories
     * @return
     */
    int insert(GfsCategories gfsCategories);

    /**
     * 查询分类信息
     *
     * @param search 搜索字段
     * @return
     */
    List<GfsCategories> select(@Param("search") String search);

    /**
     * 查询分类信息
     *
     * @param id id
     * @return
     */
    List<GfsCategories> selectById(@Param("id") Long id);

    /**
     * 修改分类信息
     *
     * @param gfsCategories
     * @return
     */
    int update(GfsCategories gfsCategories);

    /**
     * 删除分类信息
     *
     * @param ids
     */
    void delete(List<Long> ids);

    /**
     * 查询分类信息
     * @param name 分类名
     * @return
     */
    List<GfsUser> selectByName(@Param("name")String name);
}
