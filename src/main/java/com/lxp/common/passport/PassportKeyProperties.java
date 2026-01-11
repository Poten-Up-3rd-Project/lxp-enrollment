package com.lxp.common.passport;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@ConfigurationProperties(prefix = "passport.key")
public class PassportKeyProperties {

    private final String KEY_FACTORY_ALGORITHM = "RSA";

    private final String publicKeyString;
    private final PublicKey publicKey;

    public PassportKeyProperties(String publicKeyString) throws Exception {
        this.publicKeyString = publicKeyString;

        try {
            byte[] keyBytes = Base64.getDecoder().decode(publicKeyString);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_FACTORY_ALGORITHM);
            publicKey = keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            if (e instanceof IllegalArgumentException) {
                throw new Error("passport.key.public-key-string 이 유효한 Base64 형식이 아닙니다. PEM 형식이 아닌 개행없는 Base64 문자열을 제시해주시기 바랍니다.", e);
            } else if (e instanceof NullPointerException) {
                throw new Error("알 수 없는 이유로 keyBytes 가 생성되지 않았습니다.", e);
            } else if (e instanceof NoSuchAlgorithmException) {
                throw new Error("KeyFactory 가 주어진 키 알고리즘 (" + KEY_FACTORY_ALGORITHM + ") 을 찾을 수 없습니다.", e);
            } else if (e instanceof InvalidKeySpecException) {
                throw new Error("keySpec 이 부적절하여 public key 를 생성할 수 없습니다.", e);
            } else {
                throw new Error("알 수 없는 이유로 키 생성에 실패했습니다.", e);
            }
        }
    }

    public PublicKey publicKey() {
        return publicKey;
    }
}
