package org.rhett.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.rhett.admin.model.entity.Role;
import org.rhett.admin.model.entity.User;
import org.rhett.admin.model.mapper.UserMapper;
import org.rhett.admin.service.RoleService;
import org.rhett.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Rhett
 * @Date 2021/6/13
 * @Description
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private RoleService roleService;

    /**
     * 根据用户名查找用户
     * @param username 用户名
     * @return 用户对象
     */
    @Override
    public User findByUsername(String username) {
        User user = this.getOne(new QueryWrapper<User>().eq("username", username));
        //获取用户所属角色集合
        List<Role> roleList = roleService.getRoles(user.getId());
        user.setRoles(roleList);
        return user;
    }
}
