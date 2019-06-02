package me.guoxin.pojo;

public enum StoreColumn {
    ID("id", 1),
    ADDRESS("address", 2),
    PHONE("phone", 3),
    CITY("city_id", 4),
    CREATE_TIME("create_time", 5),
    UPDATE_TIME("update_time", 6);


    private String column;
    private int index;

    private StoreColumn(String column, int index) {
        this.column = column;
        this.index = index;
    }

    public static String getColunm(int index) {
        for (StoreColumn storeColumn : StoreColumn.values()) {
            if (storeColumn.getIndex() == index) {
                return storeColumn.getColumn();
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
