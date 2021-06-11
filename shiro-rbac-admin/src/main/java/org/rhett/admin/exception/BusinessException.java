package org.rhett.admin.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.rhett.admin.model.result.ResultStatus;

/**
 * @Author Rhett
 * @Date 2021/6/11
 * @Description
 * 自定义异常处理类
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BusinessException extends RuntimeException {
    ResultStatus status;

    public BusinessException(ResultStatus status) {
        //不生成栈追踪信息
        super(status.getMessage(), null, false, false);
        this.status = status;
    }

    public BusinessException(ResultStatus status, String message) {
        super(message);
        this.status = status;
    }

    public BusinessException(ResultStatus status, Throwable e) {
        super(e);
        this.status = status;
    }
}
