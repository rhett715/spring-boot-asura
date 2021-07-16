package org.rhett.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.rhett.admin.model.entity.Permission;

import java.util.List;

public interface PermissionService extends IService<Permission> {
    List<Permission> getPermissions(Integer roleId);
}
