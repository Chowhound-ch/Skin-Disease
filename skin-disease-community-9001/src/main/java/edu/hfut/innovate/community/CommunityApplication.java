package edu.hfut.innovate.community;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@MapperScan({"edu.hfut.innovate.community.dao", "edu.hfut.innovate.common.mapper"})
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "edu.hfut.innovate")
public class CommunityApplication {
    public static void main(String[] args) {
        SpringApplication.run(CommunityApplication.class);
    }
}