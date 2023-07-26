package edu.hfut.innovate.gateway.security;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;

/**
 * @author : Chowhound
 * @since : 2023/7/25 - 19:04
 */
public class CustomReactiveAuthenticationManager implements ReactiveAuthenticationManager{
    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        // 获取用户名
        String username = authentication.getName();
        // 获取密码
        String password = authentication.getCredentials().toString();
        if (password != null && password.equals("123456")) {
            return Mono.just(new UsernamePasswordAuthenticationToken(username, password, authentication.getAuthorities()));
        }

        return Mono.just(authentication);
    }
}
