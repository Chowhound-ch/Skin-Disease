package edu.hfut.innovate.identify;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author : Chowhound
 * @since : 2024/2/20 - 16:23
 */
@MapperScan({"edu.hfut.innovate.identify.mapper", "edu.hfut.innovate.common.mapper"})
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "edu.hfut.innovate")
public class IdentifyApplication {
    public static void main(String[] args) {
        SpringApplication.run(IdentifyApplication.class);
    }
}