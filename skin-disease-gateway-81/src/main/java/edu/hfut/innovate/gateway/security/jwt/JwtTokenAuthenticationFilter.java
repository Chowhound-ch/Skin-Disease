package edu.hfut.innovate.gateway.security.jwt;

import cn.hutool.core.util.StrUtil;
import edu.hfut.innovate.common.util.TokenManager;
import edu.hfut.innovate.common.vo.community.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 解析token并设置认证信息
 *
 * @author : Chowhound
 * @since : 2023/08/02 - 20:18
 */
@Component
public class JwtTokenAuthenticationFilter implements WebFilter {

    public static final String HEADER_PREFIX = "Bearer ";
    @Autowired
    private TokenManager tokenManager;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String token = resolveToken(exchange.getRequest());
        return chain.filter(exchange).contextWrite(
                ReactiveSecurityContextHolder.withAuthentication(getAuthentication(token)));

    }

    private String resolveToken(ServerHttpRequest request) {
        String bearerToken = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (!StrUtil.isBlank(bearerToken) && bearerToken.startsWith(HEADER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public Authentication getAuthentication(String token) {
        if (StrUtil.isNotBlank(token) && tokenManager.isTokenExist(token)){

            UserVo userVo = tokenManager.getUserFromToken(token);
            GrantedAuthority authority = new SimpleGrantedAuthority("USER");

            return new UsernamePasswordAuthenticationToken(userVo.getUsername(), userVo.getPhone(), List.of(authority));
        }
        return new UsernamePasswordAuthenticationToken(null, null);

    }
}