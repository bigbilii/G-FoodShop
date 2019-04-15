package me.guoxin.manager.service.impl;

import me.guoxin.dto.AddressDTO;
import me.guoxin.manager.mapper.AddressMapper;
import me.guoxin.manager.mapper.CityMapper;
import me.guoxin.manager.mapper.UserMapper;
import me.guoxin.manager.service.AddressService;
import me.guoxin.manager.service.UserService;
import me.guoxin.pojo.GfsAddress;
import me.guoxin.pojo.GfsCity;
import me.guoxin.pojo.GfsUser;
import me.guoxin.pojo.IException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {
    @Resource
    AddressMapper addressMapper;
    @Resource
    UserMapper userMapper;
    @Resource
    CityMapper cityMapper;

    @Override
    public void insert(AddressDTO addressDTO, Long id) {
        // 获取当前用户
        List<GfsUser> users = new ArrayList<>();
        try {
            users = userMapper.selectById(id);
        } catch (Exception e) {
            throw new IException("服务器错误");
        }
        if (users.isEmpty()) {
            throw new IException("当前用户不存在，请刷新页面重试");
        }
        GfsUser gfsUser = users.get(0);

        // 获取选择城市
        String cityName = addressDTO.getCityName();
        if (cityName == null || "".equals(cityName.trim())) {
            throw new IException("城市名为空，请重试");
        }
        List<GfsCity> list = new ArrayList<>();
        try {
            list = cityMapper.selectByName(cityName);
        } catch (Exception e) {
            throw new IException("服务器错误");
        }
        if (list.isEmpty()) {
            throw new IException("选择城市不在服务范围");
        }
        GfsCity gfsCity = list.get(0);

        GfsAddress gfsAddress = addressDTO;
        gfsAddress.setCity(gfsCity);
        gfsAddress.setUser(gfsUser);
        Date now = new Date();
        gfsAddress.setCreateTime(now);
        gfsAddress.setUpdateTime(now);

        if (addressMapper.insert(gfsAddress) != 1) {
            throw new IException("添加地址失败！");
        }


    }

    @Override
    public List<GfsAddress> selectByUserId(Long id) {
        List<GfsAddress> list = addressMapper.selectByUserId(id);
        for (GfsAddress gfsAddress : list) {
            gfsAddress.getUser().maskPasswordInfo();
        }
        return list;
    }

    @Override
    public void deleteById(Long id) {
        addressMapper.delete(id);
    }

    @Override
    public void deleteByUserId(Long id) {
        addressMapper.setUserIdNull(id);
    }
}
