package edu.hfut.innovate.gateway.security;

import edu.hfut.innovate.common.jackson.JacksonUtil;
import edu.hfut.innovate.common.renren.R;
import edu.hfut.innovate.common.vo.community.UserVo;
import edu.hfut.innovate.gateway.security.feign.UserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * @author : Chowhound
 * @since : 2023/7/25 - 19:04
 */
@Component
public class CustomReactiveAuthenticationManager implements ReactiveAuthenticationManager{

    @Autowired
    private UserController userController;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        // 获取用户名
        String username = authentication.getName();
        // 获取密码
        String phone = authentication.getCredentials().toString();
        if (username != null && phone != null) {
            Mono<R> res = userController.login(username, phone);
            // 如果登录成功
//            if (res.block().getCode() == 0) {
//                return Mono.just(new UsernamePasswordAuthenticationToken(username, phone, authentication.getAuthorities()));
//            }
//            ;
            return res.doFirst(() -> {
                System.out.println("登录失败");
            }).map(r ->new UsernamePasswordAuthenticationToken(username, phone, authentication.getAuthorities()));
        }

        return Mono.just(authentication);
    }
}
