package org.rhett.admin.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @Author Rhett
 * @Date 2021/6/13
 * @Description
 * 自定义Shiro Token类
 */
public class AuthToken extends UsernamePasswordToken {
    private final String token;

    public AuthToken(String token) {
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
