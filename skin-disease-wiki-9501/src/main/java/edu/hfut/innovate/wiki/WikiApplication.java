package edu.hfut.innovate.wiki;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author : Chowhound
 * @since : 2023/9/9 - 15:15
 */
@EnableDiscoveryClient
@SpringBootApplication
public class WikiApplication {
    public static void main(String[] args) {
        SpringApplication.run(WikiApplication.class, args);
    }
}