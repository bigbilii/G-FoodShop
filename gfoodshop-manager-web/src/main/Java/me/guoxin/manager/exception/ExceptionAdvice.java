package me.guoxin.manager.exception;



import me.guoxin.pojo.IException;
import me.guoxin.pojo.Result;
import me.guoxin.utils.ResultUtil;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

/**
 * 全局异常处理
 */
@RestControllerAdvice
public class ExceptionAdvice {

    private static final Logger log = LogManager.getLogger(ExceptionAdvice.class);

    /**
     * 信息无法读取
     *
     * @param e
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result handleHttpMessageNotReadableException(Exception e) {
        e.printStackTrace();
        return new ResultUtil<>().setMsg(500, "无法读取");
    }

    /**
     * 外键冲突
     *
     * @param e
     * @return
     */
    @ExceptionHandler(java.sql.SQLIntegrityConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result handleSQLIntegrityConstraintViolationException(Exception e) {
        e.printStackTrace();
        return new ResultUtil<>().setMsg(500, "不可删除，其有关联内容存在");
    }

    /**
     * 处理参数异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result handleMethodArgumentNotValidException(Exception e) {
        return new ResultUtil<>().setMsg(500, "参数验证失败");
    }


    /**
     * 数学计算错误
     *
     * @param e
     * @return
     */
    @ExceptionHandler(ArithmeticException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result handleArithmeticException(ArithmeticException e) {

        return new ResultUtil<>().setMsg(500, "服务器内部错误");
    }

    /**
     * 读写错误
     *
     * @param e
     * @return
     */
    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result handleIOException(IOException e) {
        return new ResultUtil<>().setMsg(500, "服务器内部读写错误");
    }

    /**
     * 自定义错误
     * @param e
     * @return
     */
    @ResponseStatus(value = HttpStatus.OK)
    @ExceptionHandler(IException.class)
    public Result<Object> handleIException(IException e) {
        String errorMsg = "GFS exception: ";
        if (e != null) {
            errorMsg = e.getMsg();
            log.warn(e.getMessage());
        }
        return new ResultUtil<>().setMsg(errorMsg);
    }

    /*================Shiro-START================*/
    @ExceptionHandler(AuthorizationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result handleAuthorizationException(AuthorizationException e) {
        log.error(e);
        return new ResultUtil<>().setMsg(403, "没有访问权限");
    }

    @ExceptionHandler(IncorrectCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result handleIncorrectCredentialsException(AuthenticationException e) {
        return new ResultUtil<>().setMsg(401, "用户名或密码错误");
    }

    @ExceptionHandler(UnknownAccountException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result handleUnknownAccountException(UnknownAccountException e) {
        return new ResultUtil<>().setMsg(401, "未知用户，请登录");
    }

    @ExceptionHandler(ExcessiveAttemptsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result handleUnknownAccountException(ExcessiveAttemptsException e) {
        return new ResultUtil<>().setMsg(401, "错误登录超过5次，请在30分钟后登录，或者联系管理员");
    }

    @ExceptionHandler(DisabledAccountException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result handleUnknownAccountException(DisabledAccountException e) {
        return new ResultUtil<>().setMsg(401, "账户以禁用，请联系管理员");
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result handleAuthenticationException(AuthenticationException e) {
        log.error(e);
        return new ResultUtil<>().setMsg(401, "登陆错误");
    }
    /*================Shiro-END================*/
}
