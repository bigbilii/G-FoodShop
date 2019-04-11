package me.guoxin.pojo;

import java.util.Date;
import java.util.List;

public class GfsCart {
    // 0:禁止
    public static final Integer _0 = 0;
    // 1:有效
    public static final Integer _1 = 1;

    private Long id;
    private GfsUser user;
    private List<GfsProductCart> products;
    private int status;
    private Date createTime;
    private Date updateTime;
    private double sum;

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

    public List<GfsProductCart> getProducts() {
        return products;
    }

    public void setProducts(List<GfsProductCart> products) {
        this.products = products;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public void makeAllSum() {
        for (GfsProductCart product : products) {
            sum += product.getPrice() * product.getNum();
        }
    }
}
