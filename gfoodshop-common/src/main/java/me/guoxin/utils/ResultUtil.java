package me.guoxin.utils;


import me.guoxin.pojo.Result;

public class ResultUtil<T> {

    private static String SUCCESS_MESSAGE = "success";

    private Result<T> result;

    public ResultUtil(){
        result=new Result<>();
        result.setMessage(SUCCESS_MESSAGE);
        result.setCode(200);
    }

    public Result<T> setData(T t){
        this.result.setData(t);
        this.result.setCode(200);
        return this.result;
    }

    public Result<T> setData(T t, String msg){
        this.result.setData(t);
        this.result.setCode(200);
        this.result.setMessage(msg);
        return this.result;
    }

    public Result<T> setMsg(String msg){
        this.result.setMessage(msg);
        this.result.setCode(500);
        return this.result;
    }

    public Result setMsg(Integer code, String msg){
        this.result.setMessage(msg);
        this.result.setCode(code);
        return this.result;
    }
}
