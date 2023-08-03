package edu.hfut.innovate.gateway.security;

import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
public class AccessDecision implements ReactiveAuthorizationManager<AuthorizationContext> {
    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext object) {
        return authentication.map(auth -> {
            Object authResult = auth.getPrincipal();
            //数据读取非空，说明前期在auth的时候，jwt认证返回非空
            if (Objects.nonNull(authResult)) {
                return new AuthorizationDecision(true);
            }

            return new AuthorizationDecision(false);
        });
    }
}
