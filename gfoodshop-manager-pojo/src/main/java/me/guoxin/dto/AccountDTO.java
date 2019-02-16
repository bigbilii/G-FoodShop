package me.guoxin.dto;

import org.apache.commons.lang3.StringUtils;

/**
 * 登录
 */
public class AccountDTO {
    /**
     * 账号 | 手机号
     */
    private String phone;
    /**
     * 密码
     */
    private String password;
    /**
     * 记住我
     */
    private boolean remember;

    /**
     * 验证码需要
     */
    private String geetest_challenge;
    private String geetest_validate;
    private String geetest_seccode;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String username) {
        this.phone = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRemember() {
        return remember;
    }

    public void setRemember(boolean rememberme) {
        this.remember = rememberme;
    }

    public boolean checkEnmpyInfo() {
        if (StringUtils.isBlank(phone))
            return true;
        return StringUtils.isBlank(password);
    }

    public String getGeetest_challenge() {
        return geetest_challenge;
    }

    public void setGeetest_challenge(String geetest_challenge) {
        this.geetest_challenge = geetest_challenge;
    }

    public String getGeetest_validate() {
        return geetest_validate;
    }

    public void setGeetest_validate(String geetest_validate) {
        this.geetest_validate = geetest_validate;
    }

    public String getGeetest_seccode() {
        return geetest_seccode;
    }

    public void setGeetest_seccode(String geetest_seccode) {
        this.geetest_seccode = geetest_seccode;
    }

}
