package edu.hfut.innovate.identify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author : Chowhound
 * @since : 2024/2/20 - 16:23
 */
@EnableDiscoveryClient
@SpringBootApplication
public class IdentifyApplication {
    public static void main(String[] args) {
        SpringApplication.run(IdentifyApplication.class);
    }
}