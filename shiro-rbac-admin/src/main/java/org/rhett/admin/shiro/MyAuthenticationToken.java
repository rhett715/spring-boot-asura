package org.rhett.admin.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @Author Rhett
 * @Date 2021/6/11
 * @Description
 * 自定义认证用户对象
 * 包含用户提交的账户和凭证的整合，然后交给Authenticator进行认证
 */
public class MyAuthenticationToken implements AuthenticationToken {
    private final String token;

    public MyAuthenticationToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
