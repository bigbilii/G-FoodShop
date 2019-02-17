package me.guoxin.manager.shiro;

import me.guoxin.manager.service.UserService;
import me.guoxin.pojo.GfsUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * 自定义Realm
 * 从数据库获取用户和权限信息
 * 来判断当前访问会话中的登录和授权
 */
public class MyRealm extends AuthorizingRealm {
    @Resource
    UserService userService;

    private static final Logger log = LoggerFactory.getLogger(MyRealm.class);

    /**
     * 授权验证
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("正在进行授权验证！");
        /*获取用户手机号*/
        GfsUser gfsUser = (GfsUser) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        log.info("正在从数据库获得角色信息，用户登录手机号为：" + gfsUser);
        //获得授权角色
        authorizationInfo.setRoles(userService.getRolesByUserPhone(gfsUser.getPhone()));
        log.info("正在从数据库获得权限信息，用户登录手机号为：" + gfsUser);
        //获得授权权限
        authorizationInfo.setStringPermissions(userService.getPermissionsByUserPhone(gfsUser.getPhone()));
        log.info("数据库获得信息完成，信息为：" + authorizationInfo.getRoles() + " \n" + authorizationInfo.getStringPermissions());
        return authorizationInfo;
    }

    /**
     * 登录验证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("正在进行登录验证！");

        /*获取用户手机号*/
        String userPhone = authenticationToken.getPrincipal().toString();
        log.info("正在从数据库获得用户信息，用户登录手机号为：" + userPhone);
        GfsUser gfsuser = userService.getUserByPhone(userPhone);
        log.info("数据库获得用户信息，：" + gfsuser);
        if (gfsuser == null) {
            return null;
        }

        if (GfsUser._0.equals(gfsuser.getStatus())) {
            throw new DisabledAccountException();
        }

        clearCached();
        /*返回认证信息，交给AuthenticationRealm进行密码匹配*/
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                gfsuser,
                gfsuser.getPassword(),
                ByteSource.Util.bytes(gfsuser.CredentialsSalt()),
                getName()
        );
        return authenticationInfo;
    }

    /**
     * 清除缓存
     */
    private void clearCached() {
        PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
        super.clearCache(principals);
    }
}
