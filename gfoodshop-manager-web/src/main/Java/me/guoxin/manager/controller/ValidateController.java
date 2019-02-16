package me.guoxin.manager.controller;

import me.guoxin.utils.GeetestLib;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * 申请验证码
 */
@Controller
public class ValidateController {

    @Resource
    private GeetestLib geetestLib;

    @RequestMapping(value = "/getValidate", method = RequestMethod.GET)
    public void getValidateCode(HttpServletResponse response, HttpServletRequest request) throws IOException {
        GeetestLib gtSdk = geetestLib;

        String resStr = "{}";

        // 自定义userid
        String userid = "gfsUserId";
        // 用户ip
        String ip_address = request.getRemoteAddr();

        if (ip_address == null || "".equalsIgnoreCase(ip_address.trim())) {
            ip_address = "unknow";// 这是为未知
        }

        HashMap<String, String> param = new HashMap<String, String>();
        param.put("user_id", userid); //网站用户id
        param.put("ip_address", ip_address); //传输用户请求验证时所携带的IP

        // 进行验证预处理
        int gtServerStatus = gtSdk.preProcess(param);

        // 将服务器状态设置到session中
        request.getSession().setAttribute(gtSdk.gtServerStatusSessionKey, gtServerStatus);
        // 将userid设置到session中
        request.getSession().setAttribute("userid", userid);

        resStr = gtSdk.getResponseStr();

        PrintWriter out = response.getWriter();
        out.println(resStr);
    }
}
