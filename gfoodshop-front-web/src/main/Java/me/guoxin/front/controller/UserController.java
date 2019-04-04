package me.guoxin.front.controller;

import me.guoxin.dto.AccountDTO;
import me.guoxin.dto.RegisterUserDTO;
import me.guoxin.manager.service.UserService;
import me.guoxin.pojo.GfsUser;
import me.guoxin.pojo.Result;
import me.guoxin.utils.GeetestLib;
import me.guoxin.utils.ResultUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;

@RestController()
public class UserController {
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
    public Result login(@RequestBody AccountDTO account, HttpServletRequest request) {
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
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);
            GfsUser user = userService.getUserByPhone(account.getPhone());
            user.setLastLoginTime(new Date());
            userService.updateUser(user);
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
     * 获取当前用户信息
     *
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/user/myInfo")
    public Result getMyUserInfo() {
        GfsUser gfsUser = (GfsUser) SecurityUtils.getSubject().getPrincipal();
        if (gfsUser != null) {
            gfsUser.maskPasswordInfo();
        }
        return new ResultUtil<>().setData(gfsUser);
    }
    /**
     * 注册用户
     *
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/user/register")
    public Result register(@RequestBody RegisterUserDTO account, HttpServletRequest request) {
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
            userService.register(account);
            return new ResultUtil<Object>().setData(null, "注册成功");
        } else {
            return new ResultUtil<Object>().setMsg(401, "验证错误");
        }
    }

}
