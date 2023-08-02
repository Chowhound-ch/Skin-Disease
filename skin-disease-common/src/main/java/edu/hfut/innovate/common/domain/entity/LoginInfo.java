package edu.hfut.innovate.common.domain.entity;

import lombok.Data;

/**
 * 用户登录信息
 *
 * @author : Chowhound
 * @since : 2023/7/26 - 20:15
 */
@Data
public class LoginInfo {
    private String username;

    private String phone;

    private String password;
}
