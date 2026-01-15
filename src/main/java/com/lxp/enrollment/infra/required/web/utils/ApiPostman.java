package com.lxp.enrollment.infra.required.web.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lxp.enrollment.infra.required.web.exception.ApiErrorCode;
import com.lxp.enrollment.infra.required.web.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import java.util.function.Supplier;

@Slf4j
public class ApiPostman {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * api 호출 후 응답 바디가 없으면 예외(ApiException(EMPTY_RESPONSE_BODY))를 발생시킨다.
     */
    public static <ResponseDto> ResponseDto demand(Supplier<ResponseEntity<ResponseDto>> apiCall) {
        ResponseEntity<ResponseDto> response = apiCall.get();

        ResponseDto body = response.getBody();
        if (response.getBody() == null) {
            throw new ApiException(ApiErrorCode.EMPTY_RESPONSE_BODY);
        }

        return body;
    }

    public static void dispatch(Supplier<ResponseEntity<Object>> apiCall) {
        apiCall.get();
    }
}
