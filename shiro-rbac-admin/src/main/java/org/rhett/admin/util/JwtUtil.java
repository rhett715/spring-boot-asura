package org.rhett.admin.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.rhett.admin.model.constant.SysConstant;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author Rhett
 * @Date 2021/6/11
 * @Description
 * Token工具类
 */
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtUtil {

    public static final String CURRENT_TIME = "currentTime";

    // 到期时间，此处单位：秒
    public static long expiration;
    // 密钥
    private static String secretKey;

    /**
     * 生成token
     * @param username 用户名
     * @param currentTime 当前时间
     * @return token字符串
     */
    public static String generateToken(String username, Long currentTime) {
        //账号加JWT私钥加密
        String secret = username + secretKey;
        //此处过期时间单位：毫秒
        Date expireTime = new Date(System.currentTimeMillis() + expiration);
        return JWT.create()
                //存放用户名
                .withClaim(SysConstant.TOKEN_ACCOUNT, username)
                //存放创建时间
                .withClaim(CURRENT_TIME, currentTime)
                //过期时间
                .withExpiresAt(expireTime)
                .sign(Algorithm.HMAC512(secret));
    }

    /**
     * 验证token是否正确
     * @param token 令牌
     * @return 是否正确
     */
    public static Boolean verifyToken(String token) {
        String secret = getClaimFromToken(token, SysConstant.TOKEN_ACCOUNT) + secretKey;
        //创建token验证器
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC512(secret)).build();
        try {
            jwtVerifier.verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 从token中获取自定义声明信息
     * @param token 令牌
     * @param claims 声明
     * @return 声明
     */
    public static String getClaimFromToken(String token, String claims) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(claims).asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }
}
