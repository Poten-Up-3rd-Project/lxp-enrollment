package com.lxp.common.passport;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;

@Component
public class PassportVerifier {

    private final String KEY_FACTORY_ALGORITHM = "RSA";

    private final PassportKeyProperties passportKeyProperties;
    private final PublicKey publicKey;

    public PassportVerifier(PassportKeyProperties passportKeyProperties) {

        this.passportKeyProperties = passportKeyProperties;

        String publicKeyString = passportKeyProperties.publicKeyString();

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

    public PassportClaims verify(String encodedPassport) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(publicKey)
                    .build()
                    .parseSignedClaims(encodedPassport)
                    .getPayload();

            return new PassportClaims(
                    claims.get("uid").toString(),
                    Arrays.asList(claims.get("rol").toString().split(",")),
                    claims.get("tid").toString()
            );

        } catch (ExpiredJwtException e) {
            throw new InvalidPassportException("만료된 passport");

        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidPassportException("유효하지 않은 passport");
        }
    }
}
