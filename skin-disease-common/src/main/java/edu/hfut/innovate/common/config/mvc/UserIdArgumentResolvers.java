package edu.hfut.innovate.common.config.mvc;

import edu.hfut.innovate.common.domain.entity.UserAuth;
import edu.hfut.innovate.common.util.TokenManager;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author : Chowhound
 * @since : 2024/3/1 - 17:02
 */
@Component
public class UserIdArgumentResolvers implements HandlerMethodArgumentResolver {
    private final TokenManager tokenManager;

    public UserIdArgumentResolvers(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return UserAuth.class.equals(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        //获取请求头中的token
        String token = webRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if(StringUtils.hasText(token)){
            //返回结果
            return tokenManager.getUserFromTokenWithBearer(token);
        }
        return null;
    }
}
