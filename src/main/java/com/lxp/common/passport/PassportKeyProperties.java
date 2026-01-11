package com.lxp.common.passport;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "passport.key")
public record PassportKeyProperties(
        String publicKeyString
) {
}
