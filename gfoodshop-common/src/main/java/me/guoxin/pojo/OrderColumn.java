package me.guoxin.pojo;

public enum OrderColumn {
    ID("id", 1),
    ALLPRICE("all_price", 2),
    USERID("user_id", 3),
    ADDRESSID("address_id", 4),
    STOREID("store_id", 5),
    DESCRIPTION("description", 6),
    CREATE_TIME("create_time", 7),
    SEND_TIME("send_time", 8),
    ARRIVE_TIME("arrive_time", 9),
    STATUS("status", 10);


    private String column;
    private int index;

    private OrderColumn(String column, int index) {
        this.column = column;
        this.index = index;
    }

    public static String getColunm(int index) {
        for (OrderColumn orderColumn : OrderColumn.values()) {
            if (orderColumn.getIndex() == index) {
                return orderColumn.getColumn();
            }
        }
        return null;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
