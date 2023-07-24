package edu.hfut.innovate.common.config.swagger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
/**
 * @author : Chowhound
 * @since : 2023/7/19 - 1:30
 */
@Configuration
public class SwaggerConfig {
    @Value("${spring.profiles.active}")
    private String env;
    @Bean
    public Docket api() {
        boolean flag = env.equals("dev");

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(flag); // 是否开启swagger
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Skin Disease Community",
                "Skin Disease Community",
                "1.0",
                "https://baidu.com/",
                new Contact("Chowhound", "https://baidu.com/", "825352674@qq.com"),
                "Apache 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList<>()
        );
    }

}
