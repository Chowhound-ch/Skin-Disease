package edu.hfut.innovate.common.config.mvc.exception;

/**
 * @author : Chowhound
 * @since : 2024/3/29 - 16:23
 */
public class TokenTypeNotFoundException extends RuntimeException{
    public TokenTypeNotFoundException() {
        super("错误的Token类型");
    }
}
