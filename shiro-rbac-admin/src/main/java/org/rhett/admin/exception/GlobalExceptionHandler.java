package org.rhett.admin.exception;

import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.rhett.admin.model.enumeration.ResultCode;
import org.rhett.admin.model.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author Rhett
 * @Date 2021/6/11
 * @Description
 *  全局异常处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 自定义服务异常
     * @param e 异常
     * @param request 请求
     * @return 结果
     */
    @ExceptionHandler(BusinessException.class)
    public Result<?> handlerException(BusinessException e, HttpServletRequest request) {
        log.error("execute method exception error.url is {}", request.getRequestURI(), e);
        return Result.failure(e.getStatus(), e.getMessage());
    }

    /**
     * 单独捕捉Shiro(UnauthorizedException)异常
     * 该异常为访问有权限管控的请求而该用户没有所需权限所抛出的异常
     * @param e 异常
     * @param request 请求
     * @return 结果封装
     */
    @ExceptionHandler(UnauthorizedException.class)
    public Result<?> handlerException(UnauthorizedException e, HttpServletRequest request) {
        log.error("execute method exception error.url is {}", request.getRequestURI(), e);
        return Result.failure(ResultCode.NO_PERMISSION).message("无权访问(Unauthorized):当前Subject没有此请求所需权限");
    }


    /**
     * 单独捕捉Shiro(UnauthenticatedException)异常
     * 该异常为以游客身份访问有权限管控的请求无法对匿名主体进行授权，而授权失败所抛出的异常
     * @param e 异常
     * @param request 请求
     * @return 结果封装
     */
    @ExceptionHandler(UnauthenticatedException.class)
    public Result<?> handlerException(UnauthenticatedException e, HttpServletRequest request) {
        log.error("execute method exception error.url is {}", request.getRequestURI(), e);
        return Result.failure(ResultCode.NO_PERMISSION).message("无权访问(Unauthorized):当前Subject是匿名Subject，请先登录");
    }
}
