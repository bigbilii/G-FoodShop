package me.guoxin.manager.vo;

import org.apache.commons.lang3.StringUtils;

public class Account {
    /**
     * 账号|手机号
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

    @Override
    public String toString() {
        return "Account{" +
                "phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", remember=" + remember +
                '}';
    }
}
