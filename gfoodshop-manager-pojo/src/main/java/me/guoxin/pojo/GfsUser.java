package me.guoxin.pojo;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户实体
 */
public class GfsUser implements Serializable {
    // 0:禁止登录
    public static final Integer _0 = 0;
    // 1:有效
    public static final Integer _1 = 1;
    private Long id;
    private String username;
    private String phone;
    private String password;
    private String salt;
    private Date creatTime;
    private Date lastLoginTime;
    private Integer status;
    private Integer roleId;
    private String sex;
    private String address;
    private String description;

    public GfsUser() {
    }

    public GfsUser(Long id, String username, String phone, String password, String salt, Date creatTime, Date lastLoginTime, Integer status, Integer roleId, String sex, String address, String description) {
        this.id = id;
        this.username = username;
        this.phone = phone;
        this.password = password;
        this.salt = salt;
        this.creatTime = creatTime;
        this.lastLoginTime = lastLoginTime;
        this.status = status;
        this.roleId = roleId;
        this.sex = sex;
        this.address = address;
        this.description = description;
    }

    public GfsUser(GfsUser gfsUser) {
        this.id = gfsUser.getId();
        this.username = gfsUser.getUsername();
        this.phone = gfsUser.getPhone();
        this.password = gfsUser.getPassword();
        this.salt = gfsUser.getSalt();
        this.creatTime = gfsUser.getCreatTime();
        this.lastLoginTime = gfsUser.getLastLoginTime();
        this.status = gfsUser.getStatus();
        this.roleId = gfsUser.getRoleId();
        this.sex = gfsUser.getSex();
        this.address = gfsUser.getAddress();
        this.description = gfsUser.getDescription();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String CredentialsSalt() {
        return username + salt;
    }

    public boolean RightPhone() {
        return phone.trim().length() == 11;
    }

    public void maskPasswordInfo() {
        setPassword("***");
        setSalt("***");
    }

}
