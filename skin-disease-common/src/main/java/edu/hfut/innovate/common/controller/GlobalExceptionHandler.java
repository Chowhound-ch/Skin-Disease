package edu.hfut.innovate.common.controller;

import edu.hfut.innovate.common.renren.R;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器,统一异常处理及其返回
 *
 * @author : Chowhound
 * @since : 2023/7/19 - 0:22
 */
@Configuration
@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler({Exception.class})
    public R handleException(Exception e) {

        e.printStackTrace();
        if (e instanceof BadSqlGrammarException) {
            return R.error();
        }
        return R.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }
}
