package me.guoxin.manager.service;

import me.guoxin.pojo.GfsCategories;

import java.util.List;

public interface CategoriesService {

    /**
     * 查询所有产品信息
     * @return
     */
    List<GfsCategories> getCategoriesList();

    /**
     * 查询所有产品信息
     * 不包含其产品信息
     * @return
     */
    List<GfsCategories> getCategoriesListWithoutProduct();
}
