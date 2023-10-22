package edu.hfut.innovate.gateway;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import reactivefeign.spring.config.EnableReactiveFeignClients;

@EnableReactiveFeignClients
@EnableFeignClients
@EnableDiscoveryClient
@MapperScan("edu.hfut.innovate.common.mapper")
@SpringBootApplication(scanBasePackages = "edu.hfut.innovate")
public class GatewayApplication {
    public static void main(String[] args) {


        SpringApplication.run(GatewayApplication.class);
    }
}
