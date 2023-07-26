package edu.hfut.innovate.gateway.security;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author : Chowhound
 * @since : 2023/7/25 - 18:33
 */
public class CustomPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        return null;
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return true;
    }
}
