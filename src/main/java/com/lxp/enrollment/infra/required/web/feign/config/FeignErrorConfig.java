package com.lxp.enrollment.infra.required.web.feign.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lxp.enrollment.infra.required.web.exception.ApiErrorCode;
import com.lxp.enrollment.infra.required.web.exception.ApiException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class FeignErrorConfig {

    /**
     * Feign 의 ErrorEncoder 는 응답 코드가 2xx 가 아닐때 호출됩니다.
     * HTTP 응답이 아예 없는 경우에는 ErrorEncoder 를 타지 않습니다.
     */
    @Bean
    public feign.codec.ErrorDecoder contentErrorDecoder(ObjectMapper om) {
        return (methodKey, response) -> {
            String body = null;
            try {
                if (response.body() != null) {
                    body = feign.Util.toString(response.body().asReader(java.nio.charset.StandardCharsets.UTF_8));
                    String additionalInfo = "API 호출 중 에러 발생: " + methodKey + ", 오류 응답 본문: " + body;
                    return new ApiException(ApiErrorCode.INTERNAL_API_CALL_ERROR_RESPONSE, additionalInfo);
                }
            } catch (IOException ignore) {
                return new RuntimeException("At " + methodKey + " IOException 발생");
            }

            return new ApiException(ApiErrorCode.INTERNAL_API_CALL_ERROR_RESPONSE);
        };
    }
}
