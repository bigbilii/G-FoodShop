package me.guoxin.pojo;

public class GfsProductOrder extends GfsProduct {
    private int num;
    private Long myId;
    private GfsOrder order;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Long getMyId() {
        return myId;
    }

    public void setMyId(Long myId) {
        this.myId = myId;
    }

    public GfsOrder getOrder() {
        return order;
    }

    public void setOrder(GfsOrder order) {
        this.order = order;
    }

}
