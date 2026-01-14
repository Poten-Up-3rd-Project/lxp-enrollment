package com.lxp.common.passport;

import io.jsonwebtoken.security.Keys;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Configuration
@ConfigurationProperties(prefix = "passport.key")
public class PassportKeyProperties {

    private String secretKey;

    @Bean
    public SecretKey passportSecretKey() {
        if (secretKey == null) {
            throw new Error("jwt secret key cannot be null or empty");
        } else if (secretKey.isEmpty()) {
            throw new Error("jwt secret key cannot be null or empty");
        }
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }
}
