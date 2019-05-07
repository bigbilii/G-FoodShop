package me.guoxin.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import me.guoxin.dto.DataTableViewPageDTO;
import me.guoxin.manager.mapper.CategoriesMapper;
import me.guoxin.manager.service.CategoriesService;
import me.guoxin.pojo.DataTableDTO;
import me.guoxin.pojo.GfsCategories;
import me.guoxin.pojo.GfsUser;
import me.guoxin.pojo.IException;
import me.guoxin.utils.DataTableUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
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

    public boolean hasCategoriesByName(String name) {
        List<GfsUser> list = new ArrayList<>();
        list = categoriesMapper.selectByName(name);
        return list.size() != 0;
    }

    @Override
    public void insertCategories(GfsCategories gfsCategories) {
        if (hasCategoriesByName(gfsCategories.getName())) {
            throw new IException("添加失败，分类名已经存在");
        }
        Date now = new Date();
        gfsCategories.setCreateTime(now);
        gfsCategories.setUpdateTime(now);
        if (categoriesMapper.insert(gfsCategories) != 1) {
            throw new IException("添加分类失败！");
        }
    }

    @Override
    public DataTableViewPageDTO<GfsCategories> listCategories(DataTableDTO dataTableDTO) {

        DataTableViewPageDTO<GfsCategories> dtoDataTableViewPageDTO = new DataTableViewPageDTO<>();
        int startIndex = dataTableDTO.getPage().getStart();
        int length = dataTableDTO.getPage().getLength();
        startIndex = startIndex / length + 1;
        String OrderBy = DataTableUtil.getCategoriesDataTableOrderBy(dataTableDTO.getOrder());
        PageHelper.startPage(startIndex, length, OrderBy);

        List<GfsCategories> list = categoriesMapper.select(dataTableDTO.getSearch());

        PageInfo<GfsCategories> pageInfo = new PageInfo<>(list);
        long total = pageInfo.getTotal();
        dtoDataTableViewPageDTO.setData(list);
        dtoDataTableViewPageDTO.setiTotalRecords(length);
        dtoDataTableViewPageDTO.setiTotalDisplayRecords(total);
        return dtoDataTableViewPageDTO;

    }

    @Override
    public void updateCategories(GfsCategories gfsCategories) {
        List<GfsCategories> list = categoriesMapper.selectById(gfsCategories.getId());
        if (list == null || list.isEmpty()) {
            throw new IException("更新分类发生错误，请刷新重试！");
        }
        GfsCategories categories = list.get(0);


        if (gfsCategories.getName() == null) {
            gfsCategories.setName(categories.getName());
        } else if (hasCategoriesByName(gfsCategories.getName())) {
            throw new IException("添加失败，分类名已经存在");
        }
        if (gfsCategories.getDescription() == null) {
            gfsCategories.setDescription(categories.getDescription());
        }
        if (gfsCategories.getCreateTime() == null) {
            gfsCategories.setCreateTime(categories.getCreateTime());
        }
        gfsCategories.setUpdateTime(new Date());
        if (categoriesMapper.update(gfsCategories) != 1) {
            throw new IException("修改用户失败！");
        }
    }

    @Override
    public void deleteCategories(List<Long> ids) {
        categoriesMapper.delete(ids);
    }

}
