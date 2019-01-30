package me.guoxin.manager.shiro;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.guoxin.manager.pojo.GfsUser;
import me.guoxin.manager.vo.Result;
import me.guoxin.utils.JsonUtil;
import me.guoxin.utils.ResultUtil;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.*;

/**
 * 踢出检验拦截器
 * 用于检验该用户是否被踢出
 */
public class KickoutSessionFilter extends AccessControlFilter {

    private static final Logger log = LoggerFactory.getLogger(KickoutSessionFilter.class);

    /*提出后的地址*/
    private String kickoutUrl;
    /*是否踢出当前*/
    private boolean kickoutAfter = false;
    /*最大会话数*/
    private int maxSession = 1;
    /*会话管理器*/
    private SessionManager sessionManager;
    /*缓存*/
    private Cache<String, Deque<Serializable>> cache;

    public void setKickoutUrl(String kickoutUrl) {
        this.kickoutUrl = kickoutUrl;
    }

    public void setKickoutAfter(boolean kickoutAfter) {
        this.kickoutAfter = kickoutAfter;
    }

    public void setMaxSession(int maxSession) {
        this.maxSession = maxSession;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    /*设置Cache的key的前缀*/
    public void setCacheManager(CacheManager cacheManager) {
        /*自定义缓存前缀*/
        this.cache = cacheManager.getCache("shiro-activeSessionCache");
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request,
                                      ServletResponse response, Object mappedValue) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request,
                                     ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);
        /*没有登录*/
        if (!subject.isAuthenticated() && !subject.isRemembered())
            return true;

        Session session = subject.getSession();
        GfsUser user = (GfsUser) subject.getPrincipal();
        String userPhone = user.getPhone();
        Serializable sessionId = session.getId();

        /*读取缓存 */
        Deque<Serializable> deque = cache.get(userPhone);
        if (deque == null) {
            deque = new LinkedList<Serializable>();
        }

        /*如果队列里没有此sessionId，且用户没有被踢出；放入队列*/
        if (!deque.contains(sessionId) && session.getAttribute("kickout") == null) {
            deque.push(sessionId);
            cache.put(userPhone, deque);
        }

        /*如果队列里的sessionId数超出最大会话数*/
        while (deque.size() > maxSession) {
            Serializable kickoutSessionId = null;
            /*踢出前者或者后者*/
            if (kickoutAfter) {
                kickoutSessionId = deque.removeFirst();
                cache.put(userPhone, deque);
            } else {
                kickoutSessionId = deque.removeLast();
                cache.put(userPhone, deque);
            }

            try {
                /*获取被踢出的sessionId的session对象*/
                Session kickoutSession = sessionManager.getSession(new DefaultSessionKey(kickoutSessionId));
                if (kickoutSession != null) {
                    /*设置会话的kickout属性表示踢出了*/
                    kickoutSession.setAttribute("kickout", true);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }

        /*如果被踢出了，直接退出，重定向到踢出后的地址*/
        if ((Boolean) session.getAttribute("kickout") != null && (Boolean) session.getAttribute("kickout")) {
            /*踢出*/
            try {
                subject.logout();
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            saveRequest(request);

            /*判断是不是Ajax请求*/
            if ("XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request).getHeader("X-Requested-With"))) {
                Result result = new ResultUtil<Object>().setData(300, "您已经在其他地方登录，请重新登录！");
                /*输出json串*/
                out(response, result);
            } else {
                /*重定向*/
                WebUtils.issueRedirect(request, response, kickoutUrl);
            }
            return false;
        }
        return true;
    }

    private void out(ServletResponse hresponse, Result result) {
        try {
            hresponse.setCharacterEncoding("UTF-8");
            PrintWriter out = hresponse.getWriter();
            out.println(JsonUtil.toJSonString(result));
            out.flush();
            out.close();
        } catch (Exception e) {
            log.error("KickoutSessionFilter.class 输出JSON异常，可以忽略。");
        }
    }
}
