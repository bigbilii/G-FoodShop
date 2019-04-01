package me.guoxin.manager.controller;

import me.guoxin.manager.service.RoleService;
import me.guoxin.pojo.GfsRole;
import me.guoxin.pojo.Result;
import me.guoxin.utils.ResultUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class RoleController {
    @Resource
    RoleService roleService;

    @RequiresPermissions("role:list")
    @GetMapping(value = "/role/list")
    public Result userList() {
        List<GfsRole> list = roleService.getRoleListWithoutAdmin();
        return new ResultUtil<>().setData(list);
    }
}
