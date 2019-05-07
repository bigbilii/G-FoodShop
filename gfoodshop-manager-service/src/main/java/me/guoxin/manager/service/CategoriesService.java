package me.guoxin.manager.service;

import me.guoxin.dto.DataTableViewPageDTO;
import me.guoxin.pojo.DataTableDTO;
import me.guoxin.pojo.GfsCategories;

import java.util.List;

public interface CategoriesService {

    /**
     * 查询所有分类信息
     *
     * @return
     */
    List<GfsCategories> getCategoriesList();

    /**
     * 查询所有分类信息
     * 不包含其产品信息
     *
     * @return
     */
    List<GfsCategories> getCategoriesListWithoutProduct();

    /**
     * 新增分类信息
     *
     * @param gfsCategories
     */
    void insertCategories(GfsCategories gfsCategories);

    /**
     * 查询分类信息
     * 不包含产品信息
     * 以datatable方式存储
     *
     * @param dataTableDTO
     * @return
     */
    DataTableViewPageDTO<GfsCategories> listCategories(DataTableDTO dataTableDTO);

    /**
     * 修改分类信息
     *
     * @param gfsCategories
     */
    void updateCategories(GfsCategories gfsCategories);

    /**
     * 删除分类信息
     * @param ids
     */
    void deleteCategories(List<Long> ids);
}
