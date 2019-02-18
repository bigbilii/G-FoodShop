package me.guoxin.manager.utils;

import me.guoxin.dto.OnlineUserDTO;
import me.guoxin.pojo.GfsUser;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;

import java.io.Serializable;

public class SessionUtils {

    /**
     * 从session中获取UserOnline对象
     * @param session
     * @return
     */
    public static OnlineUserDTO getSessionBo(Session session) {
        // 获取session登录信息。
        Object obj = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
        if (null == obj) {
            return null;
        }
        //确保是 SimplePrincipalCollection对象。
        if (obj instanceof SimplePrincipalCollection) {
            SimplePrincipalCollection spc = (SimplePrincipalCollection) obj;
            /**
             * 获取用户登录的，@link SampleRealm.doGetAuthenticationInfo(...)方法中
             * return new SimpleAuthenticationInfo(user,user.getPswd(), getName());的user 对象。
             */
            obj = spc.getPrimaryPrincipal();
            if (null != obj && obj instanceof GfsUser) {
                // 存储session + user 综合信息
                OnlineUserDTO userDTO = new OnlineUserDTO((GfsUser) obj);
                // 最后一次和系统交互的时间
                userDTO.setLastAccess(session.getLastAccessTime());
                // 主机的ip地址
                userDTO.setHost(session.getHost());
                // session ID
                userDTO.setSessionId(session.getId().toString());
                // session最后一次与系统交互的时间
                userDTO.setLastLoginTime(session.getLastAccessTime());
                // 回话到期 ttl(ms)
                userDTO.setTimeout(session.getTimeout());
                // session创建时间
                userDTO.setStartTime(session.getStartTimestamp());
                // 是否踢出
                userDTO.setSessionStatus(false);
                // 隐藏密码和盐
                userDTO.maskPasswordInfo();
                return userDTO;
            }
        }
        return null;
    }
}
