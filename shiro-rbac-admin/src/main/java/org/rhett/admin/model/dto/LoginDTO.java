package org.rhett.admin.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author Rhett
 * @Date 2021/6/13
 * @Description
 * 登录传输对象
 */
@Data
public class LoginDTO {
    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty("用户名")
    private String username;

    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("验证码")
    private String captcha;
}
