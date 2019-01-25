package me.guoxin.manager.exception;

import me.guoxin.manager.vo.Result;
import me.guoxin.utils.ResultUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

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
        return new ResultUtil<>().setMsg(500,"无法读取");
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
        return new ResultUtil<>().setMsg(500,"参数验证失败");
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

        return new ResultUtil<>().setMsg(500,"服务器内部错误");
    }

    /**
     * 登陆错误
     *
     * @param e
     * @return
     */
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result handleAuthenticationException(AuthenticationException e) {
        log.error(e);
        return new ResultUtil<>().setMsg(401,"登陆错误");
    }
    /**
     * 登陆错误
     *
     * @param e
     * @return
     */
    @ExceptionHandler(AuthorizationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result handleAuthorizationException(AuthorizationException e) {
        log.error(e);
        return new ResultUtil<>().setMsg(403,"没有权限");
    }

    @ExceptionHandler(IncorrectCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result handleIncorrectCredentialsException(AuthenticationException e) {
        return new ResultUtil<>().setMsg(401,"用户名或密码错误");
    }

    @ExceptionHandler(UnknownAccountException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result handleUnknownAccountException(UnknownAccountException e) {
        log.error(e);
        return new ResultUtil<>().setMsg(401,"请登录");
    }


    @ResponseStatus(value = HttpStatus.OK)
    @ExceptionHandler(IException.class)
    public Result<Object> handleIException(IException e) {
        String errorMsg = "Xmall exception: ";
        if (e != null) {
            errorMsg = e.getMsg();
            log.warn(e.getMessage());
        }
        return new ResultUtil<>().setMsg(errorMsg);
    }
}
