package edu.hfut.innovate.third.upload.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author : Chowhound
 * @since : 2023/10/9 - 19:13
 */
@Data
@ConfigurationProperties("third.qiniu")
public class QiniuProperties {
    public String accessKey;
    public String secretKey;
    public String bucket;
    public String domain;
}
