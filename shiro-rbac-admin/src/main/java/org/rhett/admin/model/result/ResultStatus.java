package org.rhett.admin.model.result;

public interface ResultStatus {
    /**
     * 错误码
     * @return int
     */
    int getCode();

    /**
     * 错误信息
     * @return String
     */
    String getMessage();
}
