package com.byb.security.Security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

@Component
public class DefaultPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        String md5Str = DigestUtils.md5DigestAsHex(rawPassword.toString().getBytes());
        return md5Str;
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String encodedRaw = DigestUtils.md5DigestAsHex(rawPassword.toString().getBytes());
//        Boolean result = encodedPassword.equals(DigestUtils.md5DigestAsHex(rawPassword.toString().getBytes()));
        Boolean result = encodedPassword.equals(encodedRaw);
        return result;
    }
}
