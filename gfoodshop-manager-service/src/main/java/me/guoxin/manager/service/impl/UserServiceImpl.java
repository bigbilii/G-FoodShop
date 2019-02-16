package me.guoxin.manager.service.impl;

import me.guoxin.pojo.IException;
import me.guoxin.manager.mapper.UserMapper;

import me.guoxin.manager.service.UserService;
import me.guoxin.pojo.GfsUser;
import me.guoxin.manager.utils.PasswordHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Resource
    private PasswordHelper passwordHelper;

    @Override
    public GfsUser getUserByPhone(String userPhone) {
        List<GfsUser> list = new ArrayList<>();
        try {
            list = userMapper.selectByPhone(userPhone);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public int addUser(GfsUser gfsUser) {

        if (hasUserByPhone(gfsUser.getPhone())) {
            throw new IException("手机号已存在，请重新输入");
        }

        if (!gfsUser.isRightPhone()) {
            throw new IException("手机号格式错误，请重新输入");
        }

        /*密码加密与盐*/
        passwordHelper.encryptPassword(gfsUser);
        /*设置创建时间*/
        gfsUser.setCreatTime(new Date());
        /*设置状态正常*/
        gfsUser.setStatus(1);
        if (userMapper.insert(gfsUser) != 1) {
            throw new IException("添加用户失败！");
        }
        return 1;
    }


    @Override
    public boolean hasUserByPhone(String userPhone) {
        List<GfsUser> list = new ArrayList<>();
        list = userMapper.selectByPhone(userPhone);
        return list.size() != 0;
    }

    @Override
    public Set<String> getRolesByUserPhone(String userPhone) {
        return userMapper.getRolesByUserPhone(userPhone);
    }

    @Override
    public Set<String> getPermissionsByUserPhone(String userPhone) {
        return userMapper.getPermissionsByUserPhone(userPhone);
    }

    @Override
    public List<GfsUser> getUserList() {
        return userMapper.getUserList();
    }
}
