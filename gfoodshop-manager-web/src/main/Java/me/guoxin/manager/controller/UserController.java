package me.guoxin.manager.controller;


import me.guoxin.manager.service.UserService;

import me.guoxin.pojo.GfsUser;
import me.guoxin.utils.GeetestLib;
import me.guoxin.utils.ResultUtil;
import me.guoxin.vo.Account;
import me.guoxin.vo.Result;
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
import java.util.HashMap;
import java.util.List;


@RestController()
public class UserController {
    private final static Logger log = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserService userService;

    @Resource
    private GeetestLib geetestLib;

    /**
     * 登录
     * POST
     *
     * @param account 登录信息
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/user/login")
    public Result login(@RequestBody Account account, HttpServletRequest request) {
        /*从session中获取验证状态值*/
        int gt_server_status_code = (Integer) request.getSession().getAttribute(geetestLib.gtServerStatusSessionKey);
        /*从session中获取userid*/
        String userid = (String) request.getSession().getAttribute("userid");
        /*获取用户ip地址*/
        String ip_address = request.getRemoteAddr();

        HashMap<String, String> param = new HashMap<String, String>();
        param.put("user_id", userid); //网站用户id
        param.put("ip_address", ip_address); //传输用户请求验证时所携带的IP

        int gtResult = 0;

        if (gt_server_status_code == 1) {
            /*gt-server正常，向gt-server进行二次验证*/
            gtResult = geetestLib.enhencedValidateRequest(account.getGeetest_challenge(), account.getGeetest_validate(), account.getGeetest_seccode(), param);
        } else {
            /*gt-server非正常情况下，进行failback模式验证*/
            gtResult = geetestLib.failbackValidateRequest(account.getGeetest_challenge(), account.getGeetest_validate(), account.getGeetest_seccode());
        }
        /*验证成功*/
        if (gtResult == 1) {
            UsernamePasswordToken token = new UsernamePasswordToken(account.getPhone(), account.getPassword(), account.isRemember());
            log.info("login：正在登录中，登录信息为：" + account);
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);
            log.info("login：登录成功");
            return new ResultUtil<Object>().setData(null, "登录成功");
        } else {
            return new ResultUtil<Object>().setMsg(401, "验证错误");
        }
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