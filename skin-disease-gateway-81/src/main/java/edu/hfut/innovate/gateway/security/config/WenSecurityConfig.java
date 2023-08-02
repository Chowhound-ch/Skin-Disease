package edu.hfut.innovate.gateway.security.config;

import edu.hfut.innovate.gateway.security.CustomPasswordEncoder;
import edu.hfut.innovate.gateway.security.CustomReactiveAuthenticationManager;
import edu.hfut.innovate.gateway.security.CustomServerAccessDeniedHandler;
import edu.hfut.innovate.gateway.security.jwt.JwtTokenAuthenticationFilter;
import edu.hfut.innovate.gateway.security.jwt.TokenLogoutHandler;
import edu.hfut.innovate.gateway.security.jwt.TokenLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * @author : Chowhound
 * @since : 2023/7/24 - 19:46
 */
//@Import(GlobalExceptionHandler.class)
@EnableWebFluxSecurity
@Configuration
public class WenSecurityConfig {
    @Autowired
    private CustomReactiveAuthenticationManager customReactiveAuthenticationManager;
    @Autowired
    private JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter;

    // 密码编码器
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new CustomPasswordEncoder();
    }

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
        // 认证管理器
        http.httpBasic().disable().authenticationManager(customReactiveAuthenticationManager)
                // 异常处理
                .exceptionHandling().accessDeniedHandler(new CustomServerAccessDeniedHandler())
                // 登出请求处理
                .and().logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutHandler(new TokenLogoutHandler())
                        .logoutSuccessHandler(new TokenLogoutSuccessHandler()))
                // 设置认证规则
                .authorizeExchange().pathMatchers(HttpMethod.OPTIONS).permitAll()
                // 任何请求需要身份认证
//                .anyExchange().authenticated().and()
                .anyExchange().permitAll().and()
                .formLogin()
                .and().csrf().disable()
                .addFilterAt(jwtTokenAuthenticationFilter, SecurityWebFiltersOrder.HTTP_BASIC);
        return http.build();
    }
}