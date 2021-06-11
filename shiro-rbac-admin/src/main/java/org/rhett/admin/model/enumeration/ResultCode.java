package org.rhett.admin.model.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.rhett.admin.model.result.ResultStatus;

@Getter
@AllArgsConstructor
public enum ResultCode implements ResultStatus {
    SUCCESS(200, "操作成功"),
    COMMON_ERROR(9999, "操作失败"),
    PARAM_ERROR(1001, "参数非法"),
    LOGIN_ERROR(1002, "登陆失败")
    ;

    private final int code;
    private final String message;
}
