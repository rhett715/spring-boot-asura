package org.rhett.admin.model.constant;

/**
 * @Author Rhett
 * @Date 2021/6/11
 * @Description
 * 系统公用常量
 */
public class SysConstant {
    /**
     * 构造函数私有化，避免被实例化
     */
    private SysConstant() {}

    /**
     * JWT用户名
     */
    public static final String TOKEN_ACCOUNT = "username";

    public static final String TOKEN_HEAD = "Authorization";

    public static final String TOKEN_PREFIX = "Bearer ";
}
