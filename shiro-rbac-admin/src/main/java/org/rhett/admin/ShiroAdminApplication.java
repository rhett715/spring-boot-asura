package org.rhett.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * @Author Rhett
 * @Date 2021/6/11
 * @Description
 */
@SpringBootApplication
@EnableOpenApi
@MapperScan("org.rhett.admin.model.mapper")
public class ShiroAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShiroAdminApplication.class, args);
    }
}
