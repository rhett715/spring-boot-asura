package org.rhett.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.rhett.admin.model.entity.User;

public interface UserService extends IService<User> {
    User findByUsername(String username);
}
