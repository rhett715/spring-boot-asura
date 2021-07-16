package org.rhett.admin.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.rhett.admin.model.constant.RedisConstant;
import org.rhett.admin.model.dto.LoginDTO;
import org.rhett.admin.model.entity.User;
import org.rhett.admin.model.enumeration.ResultCode;
import org.rhett.admin.model.result.Result;
import org.rhett.admin.service.UserService;
import org.rhett.admin.util.JwtUtil;
import org.rhett.admin.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @Author Rhett
 * @Date 2021/6/13
 * @Description
 * 登陆控制器
 */
@RestController
public class LoginController {
    @Autowired
    private DefaultKaptcha defaultKaptcha;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private UserService userService;

    /**
     * 验证码
     * @param request 请求
     * @param response 响应
     */
    @ApiOperation("获取验证码接口")
    @GetMapping("/kaptcha")
    public void GetKaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //禁止页面缓存
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        ////生成文字验证码
        String text = defaultKaptcha.createText();
        //将生成的文字验证码保存到redis缓存中，过期时间为1分钟
        redisUtil.set(RedisConstant.KAPTCHA_KEY, text, 60);

        //转成图片验证码
        BufferedImage image = defaultKaptcha.createImage(text);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        out.flush();
        out.close();
    }

    @ApiOperation("登录接口")
    @PostMapping("/user/login")
    public Result login(@RequestBody LoginDTO loginDTO, HttpServletResponse response) {
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();
        String captcha = loginDTO.getCaptcha();
        if (ObjectUtils.isEmpty(username) || ObjectUtils.isEmpty(password)) {
            return Result.failure(ResultCode.PARAM_ERROR).message("用户名、密码不能为空！");
        }
        String captchaFromCache = (String) redisUtil.get(RedisConstant.KAPTCHA_KEY);
        System.out.println("tryCode=" + captcha + "------rightCode=" + captchaFromCache);
        if (ObjectUtils.isEmpty(captcha) || ObjectUtils.isEmpty(captchaFromCache) || !captchaFromCache.equals(captcha)) {
            return Result.failure(ResultCode.VERIFICATION_CODE_ERROR);
        }
        //验证码通过后，从数据库获取用户信息
        User user = userService.findByUsername(username);
        if (ObjectUtils.isEmpty(user) || !user.getPassword().equals(password)) {
            return Result.failure(ResultCode.PARAM_ERROR).message("用户名或密码错误");
        }
        //生成token，并保存到redis中
        long currentTimeMillis = System.currentTimeMillis();
        String token = JwtUtil.generateToken(username, currentTimeMillis);
        //过期时间设为token的refreshTime
        redisUtil.set(username, token, JwtUtil.expiration);
        response.setHeader("Authorization", token);
        response.setHeader("Access-Control-Expose-Headers", "Authorization");
        //清除kaptcha验证码的缓存
        redisUtil.del(RedisConstant.KAPTCHA_KEY);

        return Result.success(token);
    }

    @ApiOperation("退出登录接口")
    @PostMapping("/user/logout")
    public Result logout() {
        //清除上下文，token

        Subject subject = SecurityUtils.getSubject();
        //注销
        subject.logout();

        return Result.success();
    }

    @ApiOperation("无权限测试接口")
    @GetMapping("/user/noAuthority")
    public Result noAuthority() {
        return Result.failure(ResultCode.NO_PERMISSION);
    }

    @ApiOperation("测试校验权限 permission")
    @GetMapping("/user/testShiroPermission")
    @RequiresPermissions("user:add")
    public Result<String> testShiroPermissions() {
        System.out.println("访问 testShiroPermissions API");
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        System.out.println(username);
        return Result.success(username);
    }

    @ApiOperation("测试校验角色role")
    @GetMapping("/user/testRole")
    @RequiresRoles("admin")
    public Result<String> testRole() {
        System.out.println("测试testRole");
        return Result.success("测试role");
    }
}
