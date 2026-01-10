package com.lxp.common.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.validation.BindException;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.stream.Collectors;

public final class FieldErrorResolver {

    public static List<ErrorResponse.FieldError> mapFieldErrors(BindException e) {
        return e.getBindingResult().getFieldErrors().stream()
                .map(error -> new ErrorResponse.FieldError(
                        error.getField(),
                        error.getDefaultMessage(),
                        error.getRejectedValue()
                ))
                .toList();
    }

    public static List<ErrorResponse.FieldError> mapFieldErrors(ConstraintViolationException e) {
        return e.getConstraintViolations().stream()
                .map(violation -> new ErrorResponse.FieldError(
                        violation.getPropertyPath().toString(),
                        violation.getMessage(),
                        violation.getInvalidValue()
                ))
                .toList();
    }

    public static List<ErrorResponse.FieldError> mapFieldErrors(HandlerMethodValidationException e) {
        return e.getParameterValidationResults().stream()
                .map(result -> new ErrorResponse.FieldError(
                        result.getMethodParameter().getParameterName(),
                        result.getResolvableErrors()
                                .stream()
                                .map(MessageSourceResolvable::getDefaultMessage)
                                .collect(Collectors.joining(", ")),
                        result.getArgument() == null ? null : result.getArgument().toString()
                ))
                .toList();
    }

    public static List<ErrorResponse.FieldError> mapFieldErrors(MethodArgumentTypeMismatchException e) {
        return List.of(
                new ErrorResponse.FieldError(
                        e.getName(),
                        "expected type: " + (e.getRequiredType() == null ? null : e.getRequiredType().getSimpleName()),
                        e.getValue() == null ? null : e.getValue().toString()
                )
        );
    }
}
