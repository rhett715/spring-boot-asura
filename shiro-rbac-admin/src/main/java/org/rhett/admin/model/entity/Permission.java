package org.rhett.admin.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;


/**
 * @Author Rhett
 * @Date 2021/6/13
 * @Description
 * 权限实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "Permission对象")
public class Permission implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "权限主键id")
    private Integer id;

    @ApiModelProperty("权限")
    private String permission;

    @ApiModelProperty("权限名")
    private String permissionName;
}
