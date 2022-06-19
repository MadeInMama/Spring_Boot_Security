package ru.boot_security.test.configs;

import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderWithDecoder implements PasswordEncoder {
    private static final byte delta = 1;

    @Override
    public String encode(CharSequence rawPassword) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < rawPassword.length(); i++) {
            sb.append((char) (rawPassword.charAt(i) + delta));
        }

        return sb.toString();
    }

    public String decode(CharSequence encodedPassword) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < encodedPassword.length(); i++) {
            sb.append((char) (encodedPassword.charAt(i) - delta));
        }

        return sb.toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encodedPassword.equals(encode(rawPassword));
    }
}
