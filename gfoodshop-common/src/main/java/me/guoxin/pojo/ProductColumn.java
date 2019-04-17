package me.guoxin.pojo;

public enum ProductColumn {
    ID("id", 1),
    NAME("name", 2),
    PRICE("price", 3),
    DESCRIPTION("description", 4),
    IMAGE("image", 5),
    CATEGORIES_ID("categories_id", 6),
    CREATE_TIME("create_time", 7),
    UPDATE_TIME("update_time", 8),
   STATUS("status", 9);


    private String column;
    private int index;

    private ProductColumn(String column, int index) {
        this.column = column;
        this.index = index;
    }

    public static String getColunm(int index) {
        for (ProductColumn productColumn : ProductColumn.values()) {
            if (productColumn.getIndex() == index) {
                return productColumn.getColumn();
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
