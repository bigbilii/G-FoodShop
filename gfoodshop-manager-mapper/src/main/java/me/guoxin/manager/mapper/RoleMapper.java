package me.guoxin.manager.mapper;

import me.guoxin.pojo.GfsRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    List<GfsRole> getRoleList();

    List<GfsRole> getUserRole(@Param("id")Integer userRoleId);
}
