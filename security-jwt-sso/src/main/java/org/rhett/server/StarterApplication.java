package org.rhett.server;

import org.rhett.server.properties.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @Author Rhett
 * @Date 2021/6/23
 * @Description
 */
@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class StarterApplication {
    public static void main(String[] args) {
        SpringApplication.run(StarterApplication.class, args);
    }
}
