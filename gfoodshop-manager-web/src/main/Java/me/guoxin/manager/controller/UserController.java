package me.guoxin.manager.controller;


import me.guoxin.manager.pojo.GfsUser;
import me.guoxin.manager.service.UserService;
import me.guoxin.manager.vo.Account;
import me.guoxin.manager.vo.Result;
import me.guoxin.utils.ResultUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;


@RestController()
public class UserController {
    private final static Logger log = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserService userService;

    @GetMapping(value = "/getValidateCode")
    public void getValidateCode(HttpServletResponse response, HttpServletRequest request) {
        // TODO: 2019/1/24 验证码获取与校验 
    }

    /**
     * 登录
     * POST
     *
     * @param account 登录信息
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/user/login")
    public Result login(@RequestBody Account account) {
        UsernamePasswordToken token = new UsernamePasswordToken(account.getPhone(), account.getPassword(), account.isRemember());
        log.info("login：正在登录中，登录信息为：" + account);
        Subject subject = SecurityUtils.getSubject();
        subject.login(token);
        log.info("login：登录成功");
        return new ResultUtil<Object>().setData(null, "登录成功");
    }

    /**
     * 登出
     *
     * @return
     */
    @GetMapping("/user/logout")
    public Result logout() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            subject.logout();
        }
        return new ResultUtil<Object>().setData(null, "退出成功");
    }

    /**
     * 新增用户
     *
     * @param gfsUser 用户实体
     * @return
     * @throws Exception
     */
    @RequiresPermissions("user:add")
    @PostMapping(value = "/user/addUser")
    public Result addUser(@RequestBody GfsUser gfsUser) {
        log.info("addUser：添加用户中，添加信息为为：" + gfsUser);
        userService.addUser(gfsUser);
        log.info("login：添加成功");
        return new ResultUtil<Object>().setData(null);
    }

    /**
     * 查询用户列表
     *
     * @return
     * @throws Exception
     */
    @RequiresPermissions("user:list")
    @GetMapping(value = "/user/list")
    public Result userList() {
        List<GfsUser> list = userService.getUserList();
        return new ResultUtil<Object>().setData(null);
    }

    /**
     * 新增用户
     *
     * @param gfsUser 用户实体
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/user/addRole")
    public String addRole(@RequestBody GfsUser gfsUser) throws Exception {
        System.out.println(gfsUser.getUsername() + gfsUser.getPassword());
        log.info("addUser：添加用户中，添加信息为为：" + gfsUser);
        userService.addUser(gfsUser);
        log.info("login：添加成功");
        return "hello world";
    }
}
