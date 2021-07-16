package org.rhett.admin.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.rhett.admin.model.entity.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
