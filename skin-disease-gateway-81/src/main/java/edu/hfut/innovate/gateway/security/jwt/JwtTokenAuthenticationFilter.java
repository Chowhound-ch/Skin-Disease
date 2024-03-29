package edu.hfut.innovate.gateway.security.jwt;

import edu.hfut.innovate.common.domain.entity.UserAuth;
import edu.hfut.innovate.common.util.TokenManager;
import io.jsonwebtoken.ExpiredJwtException;
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
        String realToken = TokenManager.getRealToken(exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION));
        return chain.filter(exchange).contextWrite(
                ReactiveSecurityContextHolder.withAuthentication(getAuthentication(realToken)));

    }

    public Authentication getAuthentication(String token) {
        UserAuth userAuth;
        try {
            userAuth = tokenManager.getUserAuth(token);
        } catch (ExpiredJwtException e) {
            userAuth = null;
        }

        if (userAuth != null){

            return new UsernamePasswordAuthenticationToken(userAuth.getUsername(), userAuth.getPhone(), Collections.emptyList());
        }
        return new UsernamePasswordAuthenticationToken(null, null);
    }
}