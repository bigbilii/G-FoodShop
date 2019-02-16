package me.guoxin.manager.mapper;


import me.guoxin.pojo.GfsUser;
import org.apache.ibatis.annotations.Param;


import java.util.List;
import java.util.Set;

public interface UserMapper {

    int insert(GfsUser user);

    List<GfsUser> selectByPhone(String userPhone);

    Set<String> getRolesByUserPhone(@Param("userPhone")String userPhone);

    Set<String> getPermissionsByUserPhone(@Param("userPhone")String userPhone);

    List<GfsUser> getUserList();
}
