package org.rhett.admin.exception;

import org.rhett.admin.model.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author Rhett
 * @Date 2021/6/11
 * @Description
 *  全局异常处理
 */
@RestControllerAdvice
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
}
