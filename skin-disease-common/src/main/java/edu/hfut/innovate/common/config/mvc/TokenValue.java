package edu.hfut.innovate.common.config.mvc;

import edu.hfut.innovate.common.util.TokenManager;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author : Chowhound
 * @since : 2024/3/29 - 16:29
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface TokenValue {
    @AliasFor("tokenType")
    String value() default TokenManager.ACCESS_TOKEN;

    @AliasFor("value")
    String tokenType() default TokenManager.ACCESS_TOKEN;
}
