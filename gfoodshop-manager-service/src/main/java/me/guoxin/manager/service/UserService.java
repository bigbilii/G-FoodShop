package me.guoxin.manager.service;


import me.guoxin.dto.DataTableViewPageDTO;
import me.guoxin.dto.OnlineUserDTO;
import me.guoxin.pojo.DataTableDTO;
import me.guoxin.pojo.GfsUser;

import java.util.List;
import java.util.Set;

public interface UserService {

    /**
     * 获取用户信息
     *
     * @param userPhone 用户登录手机号
     * @return 用户信息
     */
    GfsUser getUserByPhone(String userPhone);

    /**
     * 判断手机号是否存在
     *
     * @param userPhone 用户登录手机
     * @return
     */
    boolean hasUserByPhone(String userPhone);

    /**
     * 根据用户手机号获取角色信息
     *
     * @param userPhone
     * @return
     */
    Set<String> getRolesByUserPhone(String userPhone);

    /**
     * 根据用户手机号获取权限信息
     *
     * @param userPhone
     * @return
     */
    Set<String> getPermissionsByUserPhone(String userPhone);

    /**
     * 查询用户信息
     *
     * @param dataTableDTO
     * @return
     */
    DataTableViewPageDTO<GfsUser> listUsers(DataTableDTO dataTableDTO);

    /**
     * 获取在线用户
     *
     * @return
     */
    DataTableViewPageDTO<OnlineUserDTO> getOnlineUser(DataTableDTO dataTableDTO);

    /**
     * 踢出在线用户
     *
     * @param sessionIds 会话id集合
     */
    void kickOutOnlineUser(List<String> sessionIds);

    /**
     * 删除用户
     *
     * @param ids 用户id集合
     */
    void deleteUser(List<Long> ids);

    /**
     * 添加用户
     *
     * @param gfsUser 用户信息
     */
    void insertUser(GfsUser gfsUser);

    /**
     * 修改用户
     *
     * @param gfsUser 用户信息
     */
    void updateUser(GfsUser gfsUser);

    /**
     * 恢复用户状态
     *
     * @param id 用户id
     */
    void restoreUser(Long id);

    /**
     * 禁止用户状态
     *
     * @param id 用户id
     */
    void disableUser(Long id);
}
