package me.guoxin.manager.mapper;


import me.guoxin.pojo.GfsUser;
import org.apache.ibatis.annotations.Param;


import java.util.List;
import java.util.Set;

public interface UserMapper {

    /**
     * 增加用户
     * @param user
     * @return
     */
    int insert(GfsUser user);

    /**
     * 删除用户
     * @param ids 用户id列表
     */
    void deleteUser(List<Long> ids);

    /**
     * 更新用户信息
     * @param gfsUser
     * @return
     */
    int updateUser(GfsUser gfsUser);

    /**
     * 查询用户列表
     * @param userPhone 用户手机
     * @return
     */
    List<GfsUser> selectByPhone(@Param("userPhone")String userPhone);

    /**
     * 查询用户列表
     * @param id 用户id
     * @return
     */
    List<GfsUser> selectById(@Param("id")Long id);

    /**
     * 查询管理员信息
     * @return 管理员
     */
    List<GfsUser> selectAdmin();

    /**
     * 查询角色信息
     * @param userPhone 用户手机
     * @return
     */
    Set<String> getRolesByUserPhone(@Param("userPhone")String userPhone);

    /**
     * 查询权限信息
     * @param userPhone 用户手机
     * @return
     */
    Set<String> getPermissionsByUserPhone(@Param("userPhone")String userPhone);

    /**
     * 查询用户列表
     * @param search 用户名或者手机号
     * @return
     */
    List<GfsUser> listUsers(@Param("search")String search);

}
