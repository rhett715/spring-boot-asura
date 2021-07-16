package org.rhett.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.rhett.common.domain.Payload;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;


/**
 * @Author Rhett
 * @Date 2021/6/18
 * @Description
 * JWT工具类
 */
public class JwtUtil {
    private static final String JWT_PAYLOAD_USER_KEY = "user";

    private static String createJTI() {
        return new String(Base64.getEncoder().encode(UUID.randomUUID().toString().getBytes()));
    }

    /**
     * 生成私钥加密token
     * @param userInfo 载荷中的用户数据
     * @param privateKey 私钥
     * @param expire 过期时间，单位毫秒
     * @return 令牌
     */
    public static String generateToken(Object userInfo, PrivateKey privateKey, long expire) {
        long expiration = System.currentTimeMillis() + expire;
        return Jwts.builder()
                .claim(JWT_PAYLOAD_USER_KEY, JsonUtil.toString(userInfo))
                .setId(createJTI())
                .setExpiration(new Date(expiration))
                .signWith(SignatureAlgorithm.RS256, privateKey)
                .compact();
    }

    /**
     * 公钥解析token
     * @param token 令牌
     * @param publicKey 公钥
     * @return 载荷
     */
    public static Claims parserToken(String token, PublicKey publicKey) {
        return Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token).getBody();
    }

    /**
     * 从token中获取载荷信息
     * @param token 令牌
     * @param publicKey 公钥
     * @param <T> 载荷类型
     * @return 自定义载荷对象
     */
    public static <T> Payload<T> getInfoFromToken(String token, PublicKey publicKey) {
        Claims claims = parserToken(token, publicKey);
        Payload<T> payload = new Payload<>();
        payload.setId(claims.getId());
        payload.setExpiration(claims.getExpiration());
        return payload;
    }

    /**
     * 从token中获取用户信息
     * @param token 令牌
     * @param publicKey 公钥
     * @param userType 用户对象类型
     * @return 自定义载荷对象
     */
    public static <T> Payload<T> getUserInfoFromToken(String token, PublicKey publicKey, Class<T> userType) {
        Claims claims = parserToken(token, publicKey);
        Payload<T> payload = new Payload<>();
        payload.setId(claims.getId());
        payload.setExpiration(claims.getExpiration());
        payload.setUserInfo(JsonUtil.toBean(claims.get(JWT_PAYLOAD_USER_KEY).toString(), userType));
        return payload;
    }
}
