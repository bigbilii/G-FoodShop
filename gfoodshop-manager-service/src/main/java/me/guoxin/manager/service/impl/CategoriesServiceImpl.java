package me.guoxin.manager.service.impl;

import me.guoxin.manager.mapper.CategoriesMapper;
import me.guoxin.manager.service.CategoriesService;
import me.guoxin.pojo.GfsCategories;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CategoriesServiceImpl implements CategoriesService {
    @Resource
    CategoriesMapper categoriesMapper;

    @Override
    public List<GfsCategories> getCategoriesList() {
        return categoriesMapper.selectCategories();
    }

    @Override
    public List<GfsCategories> getCategoriesListWithoutProduct() {
        return categoriesMapper.selectCategoriesListWithoutProducts();
    }

}
