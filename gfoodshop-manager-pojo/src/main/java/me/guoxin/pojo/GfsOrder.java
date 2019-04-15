package me.guoxin.pojo;

import java.util.Date;
import java.util.List;

public class GfsOrder {
    public final static Integer STATUS_CREATE = 1;
    public final static Integer STATUS_MAKE = 2;
    public final static Integer STATUS_SEND = 3;
    public final static Integer STATUS_ARRIVE = 4;

    private Long id;
    private GfsUser user;
    private GfsAddress address;
    private List<GfsProductOrder> productOrders;
    private GfsStore store;
    private double boxPrice;
    private double allPrice;
    private double deliveryPrice;
    private int tablewareNum;
    private int status;
    private String type;
    private String description;
    private Date createTime;
    private Date sendTime;
    private Date arriveTime;

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

    public GfsAddress getAddress() {
        return address;
    }

    public void setAddress(GfsAddress address) {
        this.address = address;
    }

    public double getBoxPrice() {
        return boxPrice;
    }

    public void setBoxPrice(double boxPrice) {
        this.boxPrice = boxPrice;
    }

    public double getAllPrice() {
        return allPrice;
    }

    public void setAllPrice(double allPrice) {
        this.allPrice = allPrice;
    }

    public double getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(double deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public int getTablewareNum() {
        return tablewareNum;
    }

    public void setTablewareNum(int tablewareNum) {
        this.tablewareNum = tablewareNum;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Date getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(Date arriveTime) {
        this.arriveTime = arriveTime;
    }

    public List<GfsProductOrder> getProductOrders() {
        return productOrders;
    }

    public void setProductOrders(List<GfsProductOrder> productOrders) {
        this.productOrders = productOrders;
    }

    public GfsStore getStore() {
        return store;
    }

    public void setStore(GfsStore store) {
        this.store = store;
    }
}
