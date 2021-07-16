package org.rhett.admin.util;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author Rhett
 * @Date 2021/6/11
 * @Description
 */
@SpringBootTest
public class JwtUtilTest {
    @Test
    public void JwtTest() {
        String token = JwtUtil.generateToken("admin", System.currentTimeMillis());
        System.out.println(token);
        System.out.println(JwtUtil.verifyToken(token));
        System.out.println(JwtUtil.getClaimFromToken(token, "username"));
    }
}
