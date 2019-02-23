package me.guoxin.manager.controller;


import me.guoxin.dto.AccountDTO;
import me.guoxin.dto.DataTableViewPageDTO;
import me.guoxin.dto.OnlineUserDTO;
import me.guoxin.manager.service.UserService;

import me.guoxin.pojo.DataTableDTO;
import me.guoxin.pojo.GfsUser;
import me.guoxin.pojo.Result;
import me.guoxin.utils.DataTableUtil;
import me.guoxin.utils.GeetestLib;
import me.guoxin.utils.ResultUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


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
        gfsUser.maskPasswordInfo();
        return new ResultUtil<>().setData(gfsUser);
    }

    /**
     * 新增用户
     *
     * @param gfsUser 用户实体
     * @return
     * @throws Exception
     */
    @RequiresPermissions("user:insert")
    @PostMapping(value = "/user/insert")
    public Result insertUser(@RequestBody GfsUser gfsUser) {
        userService.insertUser(gfsUser);
        return new ResultUtil<Object>().setData(null, "添加成功");
    }

    /**
     * 更新用户
     *
     * @param gfsUser 用户实体
     * @return
     * @throws Exception
     */
    @RequiresPermissions("user:update")
    @PutMapping(value = "/user/update")
    public Result updateUser(@RequestBody GfsUser gfsUser) {
        userService.updateUser(gfsUser);
        return new ResultUtil<Object>().setData(null, "修改成功");
    }


    /**
     * 查询用户列表
     *
     * @return
     * @throws Exception
     */
    @RequiresPermissions("user:list")
    @PostMapping(value = "/user/list")
    public Result listUsers(String tbData) {
        // 处理dataTable发送Json字符串参数
        DataTableDTO dataTableDTO = DataTableUtil.getDataTableDTO(tbData);
        DataTableViewPageDTO<GfsUser> list = userService.listUsers(dataTableDTO);
        return new ResultUtil<>().setData(list);
    }

    /**
     * 删除用户
     *
     * @return
     * @throws Exception
     */
    @RequiresPermissions("user:delete")
    @DeleteMapping(value = "/user/delete")
    public Result deleteUser(@RequestBody List<Long> ids) {
        userService.deleteUser(ids);
        return new ResultUtil<>().setData(null, "删除成功");
    }

    /**
     * 获取在线用户列表
     *
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/user/list/online")
    @RequiresPermissions("onlineUser:list")
    public Result listOnlineUsers(String tbData) {
        // 处理dataTable发送Json字符串参数
        DataTableDTO dataTableDTO = DataTableUtil.getDataTableDTO(tbData);
        DataTableViewPageDTO<OnlineUserDTO> list = userService.getOnlineUser(dataTableDTO);
        return new ResultUtil<>().setData(list);
    }

    /**
     * 踢出在线用户
     *
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/user/kickout/online")
    @RequiresPermissions("onlineUser:kickout")
    public Result kickOutOnlineUser(@RequestBody List<String> sessionIds) {
        userService.kickOutOnlineUser(sessionIds);
        return new ResultUtil<>().setData(null, "踢出成功");
    }

    /**
     * 禁用用户
     *
     * @param id 用户id
     * @return
     * @throws Exception
     */
    @RequiresPermissions("user:update")
    @PutMapping(value = "/user/{id}/disable")
    public Result disableUser(@PathVariable Long id) {
        userService.disableUser(id);
        return new ResultUtil<Object>().setData(null, "修改成功");
    }

    /**
     * 恢复用户
     *
     * @param id 用户id
     * @return
     * @throws Exception
     */
    @RequiresPermissions("user:update")
    @PutMapping(value = "/user/{id}/restore")
    public Result restoreUser(@PathVariable Long id) {
        userService.restoreUser(id);
        return new ResultUtil<Object>().setData(null, "修改成功");
    }
}
