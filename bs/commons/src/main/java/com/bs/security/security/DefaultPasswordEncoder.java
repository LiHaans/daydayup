package com.bs.security.security;

import com.bs.utils.MD5;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @Auther: lihang
 * @Date: 2021-06-24 21:45
 * @Description:
 */
@Component
public class DefaultPasswordEncoder implements PasswordEncoder {

    // 加密
    @Override
    public String encode(CharSequence rawPassword) {
        return MD5.encrypt(rawPassword.toString());
    }

    // 匹配
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encodedPassword.equals(MD5.encrypt(rawPassword.toString()));
    }
}
