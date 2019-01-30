package me.guoxin.exception;

/**
 * 自定义异常
 */
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
