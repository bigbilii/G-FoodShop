package me.guoxin.dto;

import me.guoxin.pojo.GfsUser;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * 登录
 */
public class AccountDTO extends GfsUser implements Serializable {
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

    public boolean isRemember() {
        return remember;
    }

    public void setRemember(boolean rememberme) {
        this.remember = rememberme;
    }

    public boolean checkEnmpyInfo() {
        if (StringUtils.isBlank(super.getPhone()))
            return true;
        return StringUtils.isBlank(super.getPassword());
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
