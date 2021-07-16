package org.rhett.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.rhett.admin.model.entity.Role;

import java.util.List;

public interface RoleService extends IService<Role> {
    List<Role> getRoles(Integer userId);
}
