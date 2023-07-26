package edu.hfut.innovate.gateway.security.jwt;

import edu.hfut.innovate.common.util.TokenManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.logout.ServerLogoutHandler;
import reactor.core.publisher.Mono;


/**
 * @author : Chowhound
 * @since : 2023/7/24 - 16:34
 */
@Slf4j
public class TokenLogoutHandler implements ServerLogoutHandler {
    private TokenManager tokenManager;
    private RedisTemplate redisTemplate;

//    public TokenLogoutHandler(TokenManager tokenManager, RedisTemplate redisTemplate) {
//        this.tokenManager = tokenManager;
//        this.redisTemplate = redisTemplate;
//    }

    @Override
    public Mono<Void> logout(WebFilterExchange exchange, Authentication authentication) {
        log.info("logout");
        return Mono.empty();
    }
}
