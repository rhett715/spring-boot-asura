package org.rhett.admin.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.rhett.admin.model.entity.Permission;
import org.rhett.admin.model.entity.Role;
import org.rhett.admin.model.entity.User;
import org.rhett.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

/**
 * @Author Rhett
 * @Date 2021/6/12
 * @Description
 * 自定义验证鉴权方式
 * 查询用户的角色和权限信息并保存到权限管理器
 */
public class CustomRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof MyAuthenticationToken;
    }

    /**
     * 权限配置，为当前用户授予角色和权限
     * 只有当需要检测用户权限时调用此方法，例如：checkRole，checkPermission之类的
     * @param principals
     * @return 授权信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 获取登录用户名
        String username = principals.getPrimaryPrincipal().toString();
        // 查询用户
        User user = userService.findByUsername(username);
        if (ObjectUtils.isEmpty(user)) {
            return null;
        }
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        for (Role role : user.getRoles()) {
            simpleAuthorizationInfo.addRole(role.getRoleName());
            for (Permission permission : role.getPermissions()) {
                simpleAuthorizationInfo.addStringPermission(permission.getPermissionName());
            }
        }

        return simpleAuthorizationInfo;
    }

    /**
     * 认证配置
     * @param token 认证令牌
     * @return 认证信息
     * @throws AuthenticationException 认证异常
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        if (ObjectUtils.isEmpty(token.getPrincipal())) {
            return null;
        }
        // 获取用户信息
        String name = token.getPrincipal().toString();
        User user = userService.findByUsername(name);
        if (ObjectUtils.isEmpty(user)) {
            return null;
        }
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo();
        simpleAuthenticationInfo.setCredentials(user.getPassword());

        return simpleAuthenticationInfo;
    }
}
