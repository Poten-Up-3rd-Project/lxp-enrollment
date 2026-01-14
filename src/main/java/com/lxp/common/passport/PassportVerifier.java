package com.lxp.common.passport;

import com.lxp.common.passport.constant.PassportConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Arrays;

@Component
public class PassportVerifier {

    private final SecretKey key;

    public PassportVerifier(SecretKey key) {
        this.key = key;
    }

    /**
     * Passport JWT를 검증하고 클레임을 추출합니다.
     *
     * @param encodedPassport Passport JWT 문자열
     * @return PassportClaims 개체
     * @throws InvalidPassportException JWT 검증 실패 시
     */
    public PassportClaims verify(String encodedPassport) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(encodedPassport)
                    .getPayload();

            return new PassportClaims(
                    claims.get(PassportConstants.PASSPORT_USER_ID).toString(),
                    Arrays.asList(
                            claims.get(PassportConstants.PASSPORT_ROLE).toString().split(",")
                    ),
                    claims.get(PassportConstants.PASSPORT_TRACE_ID).toString()
            );

        } catch (ExpiredJwtException e) {
            throw new InvalidPassportException("Expired passport", e);

        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidPassportException("Invalid passport", e);
        }
    }
}
