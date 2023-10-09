package edu.hfut.innovate.third.upload.config;

import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : Chowhound
 * @since : 2023/9/21 - 22:01
 */
@EnableConfigurationProperties(QiniuProperties.class)
@Configuration
public class UploadManagerConfig {
    @Bean
    public UploadManager uploadManager(){
        com.qiniu.storage.Configuration cfg = new com.qiniu.storage.Configuration(Region.huanan());
        cfg.resumableUploadAPIVersion = com.qiniu.storage.Configuration.ResumableUploadAPIVersion.V2;
        return new UploadManager(cfg);
    }
}
