package edu.hfut.innovate.common.config.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author : Chowhound
 * @since : 2024/3/1 - 17:01
 */
@ConditionalOnClass(WebMvcConfigurer.class)
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private UserIdArgumentResolvers userIdArgumentResolvers;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(userIdArgumentResolvers);
    }
}
