package org.rhett.admin.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * @Author Rhett
 * @Date 2021/6/13
 * @Description
 * 角色实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "role对象")
public class Role implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "角色主键id")
    private Integer id;

    @ApiModelProperty("角色名")
    private String roleName;

    @TableField(exist = false)
    private List<Permission> permissions;
}
