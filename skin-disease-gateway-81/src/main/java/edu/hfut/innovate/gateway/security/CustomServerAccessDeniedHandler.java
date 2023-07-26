package edu.hfut.innovate.gateway.security;

import edu.hfut.innovate.common.jackson.JacksonUtil;
import edu.hfut.innovate.common.renren.R;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author : Chowhound
 * @since : 2023/7/26 - 0:48
 */
public class CustomServerAccessDeniedHandler implements ServerAccessDeniedHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException denied) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.FORBIDDEN);
        // 设置响应体
        return response.writeWith(Mono.just(
                response.bufferFactory().wrap(JacksonUtil.toJsonString(
                        R.error(HttpStatus.FORBIDDEN.value(), "权限不足")
                ).getBytes())));
    }
}
