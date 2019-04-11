package me.guoxin.pojo;

public class GfsProductCart extends GfsProduct {
    private int num;
    private GfsCart cart;
    private Long myId;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public GfsCart getCart() {
        return cart;
    }

    public void setCart(GfsCart cart) {
        this.cart = cart;
    }

    public Long getMyId() {
        return myId;
    }

    public void setMyId(Long myId) {
        this.myId = myId;
    }
}
