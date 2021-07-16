package org.rhett.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.rhett.admin.model.entity.Permission;
import org.rhett.admin.model.mapper.PermissionMapper;
import org.rhett.admin.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Rhett
 * @Date 2021/6/13
 * @Description
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<Permission> getPermissions(Integer roleId) {
        return permissionMapper.getPermissions(roleId);
    }
}
