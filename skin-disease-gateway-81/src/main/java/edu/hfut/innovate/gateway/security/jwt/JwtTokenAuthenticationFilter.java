package edu.hfut.innovate.gateway.security.jwt;

import cn.hutool.core.util.StrUtil;
import edu.hfut.innovate.common.util.TokenManager;
import edu.hfut.innovate.common.vo.community.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class JwtTokenAuthenticationFilter implements WebFilter {

    public static final String HEADER_PREFIX = "Bearer ";
    @Autowired
    private TokenManager tokenManager;

    @Autowired
    private ReactiveAuthenticationManager authenticationManager;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String token = resolveToken(exchange.getRequest());
        if (StrUtil.isNotBlank(token) && tokenManager.isTokenExist(token)) {
            Authentication authentication = getAuthentication(token);
            exchange.getAttributes();
            return chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));
//            return chain.filter(exchange)
//                    .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));
//            return authenticationManager.authenticate(authentication)
//                    // 如果认证成功，将返回的Authentication对象设置到SecurityContext中
//                    .doOnNext(SecurityContextHolder.getContext()::setAuthentication)
//                    // 然后继续执行后续的过滤器链
//                    .then(chain.filter(exchange));
        }
        return chain.filter(exchange);
    }

    private String resolveToken(ServerHttpRequest request) {
        String bearerToken = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (StrUtil.isNotBlank(bearerToken) && bearerToken.startsWith(HEADER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public Authentication getAuthentication(String token) {
        UserVo userVo = tokenManager.getUserFromToken(token);
        GrantedAuthority authority = new SimpleGrantedAuthority("USER");
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userVo.getUsername(), userVo.getPhone(), List.of(authority));

        return authenticationToken;
    }
}