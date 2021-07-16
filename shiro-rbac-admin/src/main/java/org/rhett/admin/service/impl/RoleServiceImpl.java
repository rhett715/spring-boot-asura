package org.rhett.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.rhett.admin.model.entity.Permission;
import org.rhett.admin.model.entity.Role;
import org.rhett.admin.model.mapper.RoleMapper;
import org.rhett.admin.service.PermissionService;
import org.rhett.admin.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Rhett
 * @Date 2021/6/13
 * @Description
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RoleMapper roleMapper;


    @Override
    public List<Role> getRoles(Integer userId) {
        List<Role> roleList = roleMapper.getRolesByUserId(userId);

        for (Role role :roleList) {
            List<Permission> permissionList = permissionService.getPermissions(role.getId());
            role.setPermissions(permissionList);
        }
        return roleList;
    }
}
