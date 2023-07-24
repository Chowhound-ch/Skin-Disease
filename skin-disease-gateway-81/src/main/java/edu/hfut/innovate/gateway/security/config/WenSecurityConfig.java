package edu.hfut.innovate.gateway.security.config;

import edu.hfut.innovate.gateway.security.jwt.TokenLogoutHandler;
import edu.hfut.innovate.gateway.security.jwt.TokenLogoutSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * @author : Chowhound
 * @since : 2023/7/24 - 19:46
 */
@EnableWebFluxSecurity
@Configuration
public class WenSecurityConfig {

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) throws Exception {



        http.logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutHandler(new TokenLogoutHandler())
                        .logoutSuccessHandler(new TokenLogoutSuccessHandler()))
                .authorizeExchange().pathMatchers(HttpMethod.OPTIONS).permitAll()
                // 任何请求需要身份认证
                .anyExchange().authenticated().and()
                .formLogin()//TODO 测试使用
                .and().csrf().disable();
        return http.build();
    }

/*
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                // Spring Security should completely ignore URLs starting with /resources/
                .antMatchers("/resources/**");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/public/**").permitAll().anyRequest()
                .hasRole("USER").and()
                // Possibly more configuration ...
                .formLogin() // enable form based log in
                // set permitAll for all URLs associated with Form Login
                .permitAll();
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();
        UserDetails admin = User.withDefaultPasswordEncoder()
                .password("password")
                .roles("ADMIN", "USER")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }*/

    // Possibly more bean methods ...
}