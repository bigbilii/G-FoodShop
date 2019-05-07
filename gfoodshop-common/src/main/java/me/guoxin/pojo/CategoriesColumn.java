package me.guoxin.pojo;

public enum CategoriesColumn {
    ID("id", 1),
    NAME("name", 2),
    DESCRIPTION("description", 3),
    CREATE_TIME("create_time", 4),
    UPDATE_TIME("update_time", 5);


    private String column;
    private int index;

    private CategoriesColumn(String column, int index) {
        this.column = column;
        this.index = index;
    }

    public static String getColunm(int index) {
        for (CategoriesColumn categoriesColumn : CategoriesColumn.values()) {
            if (categoriesColumn.getIndex() == index) {
                return categoriesColumn.getColumn();
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
