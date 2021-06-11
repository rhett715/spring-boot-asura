package org.rhett.admin.model.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import org.rhett.admin.model.enumeration.ResultCode;

import java.io.Serializable;

/**
 * @Author Rhett
 * @Date 2021/6/11
 * @Description
 * 统一响应结果封装
 */
@Data
@Builder
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("状态")
    private boolean success;
    @ApiModelProperty("错误状态码")
    private int code;
    @ApiModelProperty("错误响应信息")
    private String message;
    @ApiModelProperty("响应数据")
    private T result;

    //private Result() {}

    /**
     * 不带数据的成功返回结果
     * @param <T> 类型
     * @return 结果
     */
    public static <T> Result<T> success() {
        return Result.success(null);
    }

    /**
     * 带数据的成功返回结果
     * @param data 数据
     * @param <T> 类型
     * @return 结果
     */
    public static <T> Result<T> success(T data) {
        ResultBuilder<T> resultBuilder = new ResultBuilder<T>();
        return resultBuilder
                .success(true)
                .code(ResultCode.SUCCESS.getCode())
                .message(ResultCode.SUCCESS.getMessage())
                .result(data).build();
    }

    public static Result<?> failure(ResultStatus resultStatus) {
        return Result.builder()
                .success(false)
                .code(resultStatus.getCode())
                .message(resultStatus.getMessage())
                .build();
    }

    public static Result<?> failure(ResultStatus resultStatus, String message) {
        return Result.builder()
                .success(false)
                .code(resultStatus.getCode())
                .message(message)
                .build();
    }

    public static Result<?> failure(ResultStatus resultStatus, Throwable e) {
        return Result.builder()
                .success(false)
                .code(resultStatus.getCode())
                .message(resultStatus.getMessage())
                .result(e)
                .build();
    }
}
