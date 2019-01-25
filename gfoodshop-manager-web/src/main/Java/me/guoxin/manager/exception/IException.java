package me.guoxin.manager.exception;

public class IException extends RuntimeException {
    private String msg;

    public IException(String msg){
        super(msg);
        this.msg=msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
