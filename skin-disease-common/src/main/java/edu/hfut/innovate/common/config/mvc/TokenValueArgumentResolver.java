package edu.hfut.innovate.common.config.mvc;

import edu.hfut.innovate.common.util.TokenManager;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.annotation.Nonnull;

/**
 * @author : Chowhound
 * @since : 2024/3/29 - 16:29
 */
@Component
public class TokenValueArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(@Nonnull MethodParameter parameter) {
        return String.class.equals(parameter.getParameterType())
                && parameter.hasParameterAnnotation(TokenValue.class);
    }

    @Override
    public Object resolveArgument(@Nonnull MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String token = webRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if(StringUtils.hasText(token)){
            return TokenManager.getRealToken(token);
        }
        throw new IllegalArgumentException("无效的token:" + token);
    }
}
