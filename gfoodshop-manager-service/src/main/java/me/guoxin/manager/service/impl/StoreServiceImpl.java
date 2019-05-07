package me.guoxin.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import me.guoxin.dto.DataTableViewPageDTO;
import me.guoxin.manager.mapper.CityMapper;
import me.guoxin.manager.mapper.StoreMapper;
import me.guoxin.manager.mapper.StoreMapper;
import me.guoxin.manager.service.StoreService;
import me.guoxin.manager.service.StoreService;
import me.guoxin.pojo.*;
import me.guoxin.utils.DataTableUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
public class StoreServiceImpl implements StoreService {
    @Resource
    StoreMapper storeMapper;
    @Resource
    CityMapper cityMapper;

    @Override
    @Transactional
    public void insertStore(GfsStore gfsStore) {
        GfsCity gfsCity = gfsStore.getCity();
        List<GfsCity> list = cityMapper.selectByName(gfsCity.getName());
        if (list == null || list.isEmpty()) {
            if (cityMapper.insert(gfsCity) != 1) {
                throw new IException("后台错误！");
            }
        } else {
            gfsCity = list.get(0);
        }
        gfsStore.setCity(gfsCity);
        Date now = new Date();
        gfsStore.setCreateTime(now);
        gfsStore.setUpdateTime(now);
        if (storeMapper.insert(gfsStore) != 1) {
            throw new IException("添加商家失败！");
        }
    }

    @Override
    public DataTableViewPageDTO<GfsStore> listStore(DataTableDTO dataTableDTO) {

        DataTableViewPageDTO<GfsStore> dtoDataTableViewPageDTO = new DataTableViewPageDTO<>();
        int startIndex = dataTableDTO.getPage().getStart();
        int length = dataTableDTO.getPage().getLength();
        startIndex = startIndex / length + 1;
        String OrderBy = DataTableUtil.getStoreDataTableOrderBy(dataTableDTO.getOrder());
        PageHelper.startPage(startIndex, length, OrderBy);

        List<GfsStore> list = storeMapper.select(dataTableDTO.getSearch());

        PageInfo<GfsStore> pageInfo = new PageInfo<>(list);
        long total = pageInfo.getTotal();
        dtoDataTableViewPageDTO.setData(list);
        dtoDataTableViewPageDTO.setiTotalRecords(length);
        dtoDataTableViewPageDTO.setiTotalDisplayRecords(total);
        return dtoDataTableViewPageDTO;

    }

    @Override
    public void updateStore(GfsStore gfsStore) {
        List<GfsStore> list = storeMapper.selectById(gfsStore.getId());
        if (list == null || list.isEmpty()) {
            throw new IException("更新商家发生错误，请刷新重试！");
        }
        GfsStore store = list.get(0);


        if (gfsStore.getAddress() == null) {
            gfsStore.setAddress(store.getAddress());
        }
        if (gfsStore.getPhone() == null) {
            gfsStore.setPhone(store.getPhone());
        }
        if (gfsStore.getLatitude() == null) {
            gfsStore.setLatitude(store.getLatitude());
        }
        if (gfsStore.getLongitude() == null) {
            gfsStore.setLongitude(store.getLongitude());
        }
        if (gfsStore.getCity() == null) {
            gfsStore.setCity(store.getCity());
        }
        if (gfsStore.getCreateTime() == null) {
            gfsStore.setCreateTime(store.getCreateTime());
        }
        gfsStore.setUpdateTime(new Date());
        if (storeMapper.update(gfsStore) != 1) {
            throw new IException("修改商家失败！");
        }
    }

    @Override
    @Transactional
    public void deleteStore(List<Long> ids) {
        // 获取store列表
        List<GfsStore> list = storeMapper.selectByIds(ids);
        if (list == null || list.isEmpty()) {
            throw new IException("更新商家发生错误，请刷新重试！");
        }
        // 获取store的城市列表
        Set<Long> cids = new HashSet<>();
        for (GfsStore gfsStore : list) {
            cids.add(gfsStore.getCity().getId());
        }
        // 删除需要删除的store
        storeMapper.delete(ids);
        // 获取删除后的store列表
        list = storeMapper.select(null);
        // 移除还存在的城市列表
        for (GfsStore gfsStore : list) {
            Long cid = gfsStore.getCity().getId();
            if (cids.contains(cid)){
                cids.remove(cid);
            }
        }
        // 删除不存在的城市列表
        if (!cids.isEmpty()) {
            cityMapper.delete(cids);
        }
    }

}
