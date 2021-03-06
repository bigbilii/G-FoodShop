package me.guoxin.pojo;

public enum UserColumn {
    ID("id", 1),
    USERNAME("username", 2),
    PHONE("phone", 3),
    ROLE("role_id", 4),
    SEX("sex", 5),
    CREAT_TIME("create_time", 6),
    LAST_LOGIN_TIME("last_login_time", 7),
    DESCRIPTION("description", 8),
    STATUS("status", 9);


    private String column;
    private int index;

    private UserColumn(String column, int index) {
        this.column = column;
        this.index = index;
    }

    public static String getColunm(int index) {
        for (UserColumn userColumn : UserColumn.values()) {
            if (userColumn.getIndex() == index) {
                return userColumn.getColumn();
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
