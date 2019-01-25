package me.guoxin.manager.mapper;

import me.guoxin.manager.pojo.GfsUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;


import java.util.List;
import java.util.Set;

public interface UserMapper {

    int insert(GfsUser user) throws DataAccessException;

    List<GfsUser> selectByPhone(String userPhone);

    Set<String> getRolesByUserPhone(@Param("userPhone")String userPhone);

    Set<String> getPermissionsByUserPhone(@Param("userPhone")String userPhone);

    List<GfsUser> getUserList();
}
