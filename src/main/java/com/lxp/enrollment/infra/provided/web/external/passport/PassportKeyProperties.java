package com.lxp.enrollment.infra.provided.web.external.passport;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "passport.key")
public record PassportKeyProperties(
        String publicKeyString
) {

    public PassportKeyProperties {
        if (publicKeyString == null) {
            throw new Error("환경 변수 passport.key.public-key-string 이 없습니다. 설정 파일에 없거나 올바른 프로파일을 선택해주십시오.");
        }
    }
}
