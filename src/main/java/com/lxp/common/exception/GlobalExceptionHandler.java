package com.lxp.common.exception;

import com.lxp.common.domain.exception.DomainException;
import com.lxp.common.exception.ErrorResponse.FieldError;
import com.lxp.enrollment.infra.provided.web.external.passport.InvalidPassportException;
import jakarta.servlet.ServletException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

import static com.lxp.common.exception.CommonErrorCode.DATA_ACCESS_FAILED;
import static com.lxp.common.exception.CommonErrorCode.MISSING_HTTP_HEADER;
import static com.lxp.common.exception.CommonErrorCode.MISSING_REQUEST_PARAMETER;
import static com.lxp.common.exception.CommonErrorCode.SERVLET_EXCEPTION;
import static com.lxp.common.exception.CommonErrorCode.UNEXPECTED_SERVER_ERROR;
import static com.lxp.common.exception.CommonErrorCode.UNSUPPORTED_HTTP_METHOD;
import static com.lxp.common.exception.CommonErrorCode.VALIDATION_FAILED;

@Slf4j
@RestControllerAdvice(name = "GlobalExceptionHandlerForEnrollment")
public class GlobalExceptionHandler {

    // ---------- HTTP exceptions handlers

    @ResponseBody
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {

        String errorCode = UNSUPPORTED_HTTP_METHOD.getCode();
        String errorMessage = UNSUPPORTED_HTTP_METHOD.getMessage() + ", supported: "
                + (e.getSupportedHttpMethods() == null ? null : e.getSupportedHttpMethods().toString())
                + ", given: "
                + e.getMethod();
        ErrorResponse body = ErrorResponse.of(errorCode, errorMessage);

        log.info("{}: {}", errorCode, errorMessage);
        log.debug("{}: {}", errorCode, errorMessage, e);

        return ResponseEntity
                .badRequest()
                .body(body);
    }

    @ResponseBody
    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ErrorResponse> handleMissingRequestHeaderException(MissingRequestHeaderException e) {

        String errorCode = MISSING_HTTP_HEADER.getCode();
        String errorMessage = MISSING_HTTP_HEADER.getMessage() + "required: " + e.getHeaderName();
        ErrorResponse body = ErrorResponse.of(errorCode, errorMessage);

        log.info("{}: {}", errorCode, errorMessage);
        log.debug("{}: {}", errorCode, errorMessage, e);

        return ResponseEntity
                .badRequest()
                .body(body);
    }

    @ResponseBody
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {

        String errorCode = MISSING_REQUEST_PARAMETER.getCode();
        String errorMessage = MISSING_REQUEST_PARAMETER.getMessage()
                + ", required: [" + e.getParameterType() + "]" + e.getParameterName();
        ErrorResponse body = ErrorResponse.of(errorCode, errorMessage);

        log.info("{}: {}", errorCode, errorMessage);
        log.debug("{}: {}", errorCode, errorMessage, e);

        return ResponseEntity
                .badRequest()
                .body(body);
    }

    @ResponseBody
    @ExceptionHandler(ServletException.class)
    public ResponseEntity<ErrorResponse> handleServletException(ServletException e) {

        String errorCode = SERVLET_EXCEPTION.getCode();
        String errorMessage = SERVLET_EXCEPTION.getMessage();
        ErrorResponse body = ErrorResponse.of(errorCode, errorMessage);

        log.info("{}: {}", errorCode, errorMessage);
        log.debug("{}: {}", errorCode, errorMessage, e);

        return ResponseEntity
                .internalServerError()
                .body(body);
    }

    // ---------- Validation exception handlers

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        List<FieldError> fieldErrors = FieldErrorResolver.mapFieldErrors(e);
        return handleFieldValidateException(fieldErrors, e);
    }

    @ResponseBody
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> handleBindException(BindException e) {

        List<FieldError> fieldErrors = FieldErrorResolver.mapFieldErrors(e);
        return handleFieldValidateException(fieldErrors, e);
    }

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException e) {

        List<FieldError> fieldErrors = FieldErrorResolver.mapFieldErrors(e);
        return handleFieldValidateException(fieldErrors, e);
    }

    @ResponseBody
    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ErrorResponse> handleMethodValidationException(HandlerMethodValidationException e) {

        List<FieldError> fieldErrors = FieldErrorResolver.mapFieldErrors(e);
        return handleFieldValidateException(fieldErrors, e);
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {

        List<FieldError> fieldErrors = FieldErrorResolver.mapFieldErrors(e);
        return handleFieldValidateException(fieldErrors, e);
    }

    // ---------- Domain exception handlers

    @ResponseBody
    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ErrorResponse> handleDomainException(DomainException e) {

        String errorCode = e.getCode();
        String errorMessage = e.getMessage();
        ErrorResponse body = ErrorResponse.of(errorCode, errorMessage);

        log.info("{}: {}", errorCode, errorMessage);
        log.debug("{}: {}", errorCode, errorMessage, e);

        return ResponseEntity
                .status(mapToHttpStatus(e.getGroup()))
                .body(body);
    }

    @ResponseBody
    @ExceptionHandler(InvalidPassportException.class)
    public ResponseEntity<ErrorResponse> handleInvalidPassportException(InvalidPassportException e) {

        String errorCode = "INVALID_PASSPORT_ENROLLMENT";
        String errorMessage = e.getMessage();
        ErrorResponse body = ErrorResponse.of(errorCode, errorMessage);

        log.info("{}: {}", errorCode, errorMessage);
        log.debug("{}: {}", errorCode, errorMessage, e);

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(body);
    }

    // ---------- Persistence exception handlers

    @ResponseBody
    @ExceptionHandler(DataAccessException.class)

    public ResponseEntity<ErrorResponse> handleDataAccessException(DataAccessException e) {

        String errorCode = DATA_ACCESS_FAILED.getCode();
        String errorMessage = DATA_ACCESS_FAILED.getMessage();
        ErrorResponse body = ErrorResponse.of(errorCode, errorMessage);

        log.info("{}: {}", errorCode, errorMessage);
        log.debug("{}: {}", errorCode, errorMessage, e);

        return ResponseEntity
                .internalServerError()
                .body(body);
    }

    // ---------- Uncaught(unexpected) exception handlers

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {

        String errorCode = UNEXPECTED_SERVER_ERROR.getCode();
        String errorMessage = UNEXPECTED_SERVER_ERROR.getMessage();
        ErrorResponse body = ErrorResponse.of(errorCode, errorMessage);

        log.info("{}: {}", errorCode, errorMessage);
        log.debug("{}: {}", errorCode, errorMessage, e);

        return ResponseEntity
                .internalServerError()
                .body(body);
    }

    // ---------- helpers

    private ResponseEntity<ErrorResponse> handleFieldValidateException(List<FieldError> fieldErrors, Throwable e) {

        String errorCode = VALIDATION_FAILED.getCode();
        String errorMessage = VALIDATION_FAILED.getMessage();
        ErrorResponse body = ErrorResponse.of(errorCode, errorMessage);

        log.info("{}: {}", errorCode, errorMessage);
        log.debug("{}: {}, \n 상세 원인: {}", errorCode, errorMessage, fieldErrors, e);

        return ResponseEntity
                .badRequest()
                .body(body);
    }

    private HttpStatus mapToHttpStatus(String group) {
        if (group == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return switch (group.toUpperCase()) {
            case "NOT_FOUND" -> HttpStatus.NOT_FOUND;
            case "BAD_REQUEST", "INVALID" -> HttpStatus.BAD_REQUEST;
            case "CONFLICT" -> HttpStatus.CONFLICT;
            case "FORBIDDEN" -> HttpStatus.FORBIDDEN;
            case "UNAUTHORIZED" -> HttpStatus.UNAUTHORIZED;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }
}
