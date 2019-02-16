package me.guoxin.manager.shiro;


import me.guoxin.pojo.IException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 登录次数验证 + 密码加密验证
 * 密码相关参数由配置文件配置
 */
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {

    private Cache<String, AtomicInteger> passwordRetryCache;

    /**
     * 获取 passwordRetryCache 缓存
     *
     * @param cacheManager 配置文件注入 CacheManager
     */
    public RetryLimitHashedCredentialsMatcher(CacheManager cacheManager) {
        passwordRetryCache = cacheManager.getCache("passwordRetryCache");
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) throws IException {
        /*获取用户手机号*/
        String userPhone = token.getPrincipal().toString();

        /*获取已重试次数*/
        AtomicInteger retryCount = passwordRetryCache.get(userPhone);

        /*第一次*/
        if (retryCount == null) {
            retryCount = new AtomicInteger();
            passwordRetryCache.put(userPhone, retryCount);
        }

        if (retryCount.incrementAndGet() > 5) {
            throw new ExcessiveAttemptsException();
        }
        passwordRetryCache.put(userPhone, retryCount);
        /*否则走判断密码逻辑*/
        boolean matches = super.doCredentialsMatch(token, info);
        if (matches) {
            /*clear retry count*/
            passwordRetryCache.remove(userPhone);
        }
        return matches;
    }
}
