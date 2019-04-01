package me.guoxin.pojo;

import java.util.Date;
import java.util.List;

public class GfsCategories {

    private Long id;
    private String name;
    private Date createTime;
    private Date updateTime;
    private String description;
    private List<GfsProduct> products;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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

    public List<GfsProduct> getProducts() {
        return products;
    }

    public void setProducts(List<GfsProduct> products) {
        this.products = products;
    }
}
