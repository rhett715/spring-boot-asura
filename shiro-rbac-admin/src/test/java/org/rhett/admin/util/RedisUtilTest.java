package org.rhett.admin.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author Rhett
 * @Date 2021/6/14
 * @Description
 */
@SpringBootTest
public class RedisUtilTest {
    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void RedisTest() {
        redisUtil.set("testKey", "dashu", 30);
        System.out.println(redisUtil.get("testKey").toString());
    }
}
