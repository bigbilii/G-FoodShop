package me.guoxin.dto;

import me.guoxin.pojo.GfsRole;
import me.guoxin.pojo.GfsUser;

import java.io.Serializable;
import java.util.Date;

public class RegisterUserDTO extends GfsUser implements Serializable {
    /**
     * 验证码需要
     */
    private String geetest_challenge;
    private String geetest_validate;
    private String geetest_seccode;

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
