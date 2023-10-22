package edu.hfut.innovate.gateway.security.jwt;

import cn.hutool.core.util.StrUtil;
import edu.hfut.innovate.common.domain.entity.UserAuth;
import edu.hfut.innovate.common.util.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Collections;

/**
 * 解析token并设置认证信息
 *
 * @author : Chowhound
 * @since : 2023/08/02 - 20:18
 */
@Component
public class JwtTokenAuthenticationFilter implements WebFilter {

    @Autowired
    private TokenManager tokenManager;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String token = tokenManager.resolveToken(
                exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION));
        return chain.filter(exchange).contextWrite(
                ReactiveSecurityContextHolder.withAuthentication(getAuthentication(token)));

    }

    public Authentication getAuthentication(String token) {
        if (StrUtil.isNotBlank(token) && tokenManager.isTokenExist(token)){

            UserAuth auth = tokenManager.getUserFromToken(token);
            // token存在，但是用户不存在，返回一个空的认证信息
            if (auth == null || auth.getUserId() == null) {
                return new UsernamePasswordAuthenticationToken(null, null);
            }

            return new UsernamePasswordAuthenticationToken(auth.getUsername(), auth.getPhone(), Collections.emptyList());
        }
        return new UsernamePasswordAuthenticationToken(null, null);
    }
}