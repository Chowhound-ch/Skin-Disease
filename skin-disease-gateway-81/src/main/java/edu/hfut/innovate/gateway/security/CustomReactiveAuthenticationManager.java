package edu.hfut.innovate.gateway.security;

import edu.hfut.innovate.common.renren.R;
import edu.hfut.innovate.common.util.TokenManager;
import edu.hfut.innovate.gateway.security.feign.UserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @author : Chowhound
 * @since : 2023/7/25 - 19:04
 */
@Component
public class CustomReactiveAuthenticationManager implements ReactiveAuthenticationManager{

    @Autowired
    private UserController userController;
    @Autowired
    private TokenManager tokenManager;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {

        // 获取用户名
        String username = authentication.getName();
        // 获取密码
        String phone = authentication.getCredentials().toString();
        if (username != null && phone != null) {
            Mono<R> res = userController.login(username, phone);

            return res.doFirst(() -> {
                System.out.println("登录失败");
            }).map(r ->new UsernamePasswordAuthenticationToken(username, phone, authentication.getAuthorities()));
        }

        return Mono.just(authentication);
    }
}
