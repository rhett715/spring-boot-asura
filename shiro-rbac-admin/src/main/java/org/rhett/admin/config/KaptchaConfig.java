package org.rhett.admin.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @Author Rhett
 * @Date 2021/6/13
 * @Description
 * Kaptcha配置类
 */
@Configuration
public class KaptchaConfig {

    @Bean
    public DefaultKaptcha getKaptcha() {
        Properties properties = new Properties();
        //图片边框
        properties.setProperty("kaptcha.border", "yes");
        //边框颜色
        properties.setProperty("kaptcha.border.color", "105,179,90");
        //字体颜色
        properties.setProperty("kaptcha.textproducer.font.color", "red");
        //图片宽度
        properties.setProperty("kaptcha.image.width", "110");
        //图片高度
        properties.setProperty("kaptcha.image.height", "40");
        //字体大小
        properties.setProperty("kaptcha.textproducer.font.size", "30");
        //验证码长度
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        //字体
        properties.setProperty("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
