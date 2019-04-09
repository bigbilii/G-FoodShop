package me.guoxin.pojo;

import java.util.Date;

public class GfsAddress {
    private Long id;
    private String receiverName;
    private String receiverPhone;
    private String receiverAddress;
    private String appendReceiverAddress;
    private double longitude;
    private double latitude;
    private GfsUser user;
    private GfsCity city;
    private Date createTime;
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GfsUser getUser() {
        return user;
    }

    public void setUser(GfsUser user) {
        this.user = user;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getAppendReceiverAddress() {
        return appendReceiverAddress;
    }

    public void setAppendReceiverAddress(String appendReceiverAddress) {
        this.appendReceiverAddress = appendReceiverAddress;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public GfsCity getCity() {
        return city;
    }

    public void setCity(GfsCity city) {
        this.city = city;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
