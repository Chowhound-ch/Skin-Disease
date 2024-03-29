package edu.hfut.innovate.common.config.mvc;

import edu.hfut.innovate.common.config.mvc.exception.TokenTypeNotFoundException;
import edu.hfut.innovate.common.domain.entity.UserAuth;
import edu.hfut.innovate.common.util.TokenManager;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.annotation.Nonnull;
import java.util.Objects;

/**
 * @author : Chowhound
 * @since : 2024/3/1 - 17:02
 */
@Component
public class UserIdArgumentResolver implements HandlerMethodArgumentResolver {
    private final TokenManager tokenManager;
    public static final String TOKEN_TYPE = "tokenType";

    public UserIdArgumentResolver(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return UserAuth.class.equals(parameter.getParameterType()) &&
                parameter.hasParameterAnnotation(TokenUser.class);
    }

    @Override
    public Object resolveArgument(@Nonnull MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        //获取请求头中的token
        String token = webRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if(StringUtils.hasText(token)){
            Object tokenType = AnnotationUtils.getValue(parameter.getParameterAnnotation(TokenUser.class), TOKEN_TYPE);
            if (tokenType == null) {
                return null;
            }
            if (!Objects.equals(tokenType, TokenManager.ACCESS_TOKEN) && !Objects.equals(tokenType, TokenManager.REFRESH_TOKEN)){
                throw new TokenTypeNotFoundException();
            }
            //返回结果
            return tokenManager.getUserAuth(token, (String) tokenType);
        }
        return null;
    }
}
