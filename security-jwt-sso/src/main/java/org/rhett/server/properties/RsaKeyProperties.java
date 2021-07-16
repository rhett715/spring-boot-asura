package org.rhett.server.properties;

import lombok.Data;
import org.rhett.common.util.RsaUtil;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @Author Rhett
 * @Date 2021/6/23
 * @Description
 * 提供公钥私钥的配置类
 */
@Data
@ConfigurationProperties(prefix = "rsa")
public class RsaKeyProperties {
    private String publicKeyFile;
    private String privateKeyFile;

    private PublicKey publicKey;
    private PrivateKey privateKey;

    /**
     * 系统启动的时候触发
     * @throws Exception 异常
     */
    @PostConstruct
    public void createRsaKey() throws Exception {
        publicKey = RsaUtil.getPublicKey(publicKeyFile);
        privateKey = RsaUtil.getPrivateKey(privateKeyFile);
    }
}
