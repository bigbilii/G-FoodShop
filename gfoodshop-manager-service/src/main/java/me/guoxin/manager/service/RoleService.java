package me.guoxin.manager.service;

import me.guoxin.pojo.GfsRole;

import java.util.List;

public interface RoleService {

    List<GfsRole> getRoleListWithoutAdmin();

    List<GfsRole> getRoleList();

    List<GfsRole> getUserRole();
}
