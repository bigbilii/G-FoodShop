package me.guoxin.manager.service.impl;

import me.guoxin.manager.mapper.RoleMapper;
import me.guoxin.manager.service.RoleService;
import me.guoxin.pojo.GfsRole;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    RoleMapper roleMapper;

    @Override
    public List<GfsRole> getRoleListWithoutAdmin() {
        List<GfsRole> list = getRoleList();
        for (GfsRole gfsRole : list) {
            if (gfsRole.getId() == GfsRole.ADMIN) {
                list.remove(gfsRole);
                break;
            }
        }
        return list;
    }

    @Override
    public List<GfsRole> getRoleList() {
        return roleMapper.getRoleList();
    }

    @Override
    public List<GfsRole> getUserRole() {
        return roleMapper.getUserRole(GfsRole.USER);
    }
}
