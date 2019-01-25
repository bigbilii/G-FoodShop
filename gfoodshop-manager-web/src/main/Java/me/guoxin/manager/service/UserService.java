package me.guoxin.manager.service;

import me.guoxin.manager.pojo.GfsUser;

import java.util.List;
import java.util.Set;

public interface UserService {

    /**
     * 获取用户信息
     * @param userPhone 用户登录手机号
     * @return 用户信息
     */
    GfsUser getUserByPhone(String userPhone);

    /**
     * 添加用户信息
     * @param gfsUser 用户信息
     * @return
     */
    int addUser(GfsUser gfsUser);

    /**
     * 判断手机号是否存在
     * @param userPhone 用户登录手机
     * @return
     */
    boolean hasUserByPhone(String userPhone);

    /**
     * 根据用户手机号获取角色信息
     * @param userPhone
     * @return
     */
    Set<String> getRolesByUserPhone(String userPhone);

    /**
     * 根据用户手机号获取权限信息
     * @param userPhone
     * @return
     */
    Set<String> getPermissionsByUserPhone(String userPhone);

    /**
     * 查询用户信息
     * @return
     */
    List<GfsUser> getUserList();
}
