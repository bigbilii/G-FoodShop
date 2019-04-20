package me.guoxin.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import me.guoxin.dto.DataTableViewPageDTO;
import me.guoxin.manager.mapper.ProductMapper;
import me.guoxin.manager.service.ProductService;
import me.guoxin.pojo.DataTableDTO;
import me.guoxin.pojo.GfsOrder;
import me.guoxin.pojo.GfsProduct;
import me.guoxin.pojo.IException;
import me.guoxin.utils.DataTableUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Resource
    ProductMapper productMapper;

    @Override
    public DataTableViewPageDTO<GfsProduct> getProductList(DataTableDTO dataTableDTO) {
        DataTableViewPageDTO<GfsProduct> dtoDataTableViewPageDTO = new DataTableViewPageDTO<>();
        int startIndex = dataTableDTO.getPage().getStart();
        int length = dataTableDTO.getPage().getLength();
        startIndex = startIndex / length + 1;
        String OrderBy = DataTableUtil.getProductDataTableOrderBy(dataTableDTO.getOrder());
        PageHelper.startPage(startIndex, length, OrderBy);

        List<GfsProduct> products = productMapper.select(dataTableDTO.getSearch());

        PageInfo<GfsProduct> pageInfo = new PageInfo<>(products);
        long total = pageInfo.getTotal();
        dtoDataTableViewPageDTO.setData(products);
        dtoDataTableViewPageDTO.setiTotalRecords(length);
        dtoDataTableViewPageDTO.setiTotalDisplayRecords(total);
        return dtoDataTableViewPageDTO;
    }

    @Override
    public void insertProduct(GfsProduct gfsProduct) {
        if (gfsProduct.getPrice() <= 0) {
            throw new IException("价格设置错误");
        }
        Date now = new Date();
        gfsProduct.setCreateTime(now);
        gfsProduct.setUpdateTime(now);
        gfsProduct.setStatus(1);
        if (productMapper.insert(gfsProduct) != 1) {
            throw new IException("添加商品失败！");
        }
    }

    @Override
    public void updateProduct(GfsProduct gfsProduct) {
        List<GfsProduct> list = productMapper.selectById(gfsProduct.getId());
        if (list == null || list.isEmpty()) {
            throw new IException("更新商品发生错误，请刷新重试！");
        }
        GfsProduct product = list.get(0);
        if (gfsProduct.getName() == null) {
            gfsProduct.setName(product.getName());
        }
        if (gfsProduct.getPrice() == null || gfsProduct.getPrice() <= 0) {
            gfsProduct.setPrice(product.getPrice());
        }
        if (gfsProduct.getImage() == null) {
            gfsProduct.setImage(product.getImage());
        }
        if (gfsProduct.getCategories() == null) {
            gfsProduct.setCategories(product.getCategories());
        }
        if (gfsProduct.getDescription() == null) {
            gfsProduct.setDescription(product.getDescription());
        }
        if (gfsProduct.getStatus() == null) {
            gfsProduct.setStatus(product.getStatus());
        }
        if (gfsProduct.getCreateTime() == null) {
            gfsProduct.setCreateTime(product.getCreateTime());
        }
        if (gfsProduct.getUpdateTime() == null) {
            gfsProduct.setUpdateTime(product.getUpdateTime());
        }
        gfsProduct.setUpdateTime(new Date());
        if (productMapper.update(gfsProduct) != 1) {
            throw new IException("修改用户失败！");
        }
    }

    @Override
    public void disable(Long id) {
        List<GfsProduct> list = productMapper.selectById(id);
        if (list == null || list.isEmpty()) {
            throw new IException("更新用户发生错误，可能用户被删除，请刷新重试！");
        }
        GfsProduct gfsProduct = list.get(0);
        gfsProduct.setStatus(2);
        if (productMapper.update(gfsProduct) != 1) {
            throw new IException("修改用户失败！");
        }
    }

    @Override
    public void restore(Long id) {
        List<GfsProduct> list = productMapper.selectById(id);
        if (list == null || list.isEmpty()) {
            throw new IException("更新用户发生错误，可能用户被删除，请刷新重试！");
        }
        GfsProduct gfsProduct = list.get(0);
        gfsProduct.setStatus(1);
        if (productMapper.update(gfsProduct) != 1) {
            throw new IException("修改用户失败！");
        }
    }

    @Override
    public void deleteProduct(List<Long> ids) {
        productMapper.delete(ids);
    }
}
