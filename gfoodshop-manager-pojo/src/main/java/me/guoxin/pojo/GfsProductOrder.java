package me.guoxin.pojo;

public class GfsProductOrder extends GfsProduct {
    private int num;
    private GfsOrder order;


    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public GfsOrder getOrder() {
        return order;
    }

    public void setOrder(GfsOrder order) {
        this.order = order;
    }

    public void hello(GfsProduct product) {
        setId(product.getId());
        setCategories(product.getCategories());
        setCreateTime(product.getCreateTime());
        setDescription(product.getDescription());
        setImage(product.getImage());
        setName(product.getName());
        setPrice(product.getPrice());
        setStatus(product.getStatus());
        setUpdateTime(product.getUpdateTime());
    }

}
