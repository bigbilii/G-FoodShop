package me.guoxin.dto;

import me.guoxin.pojo.IException;

import java.io.Serializable;

public class PasswordDTO implements Serializable {
    private String oldPassword;
    private String newPassword;
    private String newPasswordSure;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPasswordSure() {
        return newPasswordSure;
    }

    public void setNewPasswordSure(String newPasswordSure) {
        this.newPasswordSure = newPasswordSure;
    }

    public boolean newPasswordIsSure() {
        if (newPassword == null || newPassword.equals("")) {
            throw new IException("新密码不为空");
        }
        if (newPasswordSure == null || newPasswordSure.equals("")) {
            throw new IException("重复新密码不为空");
        }
        return newPassword.equals(newPassword);
    }
}
