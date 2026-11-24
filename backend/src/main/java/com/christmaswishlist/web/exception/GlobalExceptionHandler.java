package com.christmaswishlist.web.exception;

import com.christmaswishlist.domain.exception.BusinessException;
import com.christmaswishlist.domain.exception.ResourceNotFoundException;
import com.christmaswishlist.domain.exception.ValidationException;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> notFound(ResourceNotFoundException ex, HttpServletRequest request) {
        return response(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiError> conflict(BusinessException ex, HttpServletRequest request) {
        return response(HttpStatus.CONFLICT, ex.getMessage(), request);
    }

    @ExceptionHandler({ValidationException.class, IllegalArgumentException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<ApiError> badRequest(RuntimeException ex, HttpServletRequest request) {
        return response(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> invalidBody(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));
        return response(HttpStatus.BAD_REQUEST, message, request);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ApiError> invalidParameter(HandlerMethodValidationException ex, HttpServletRequest request) {
        return response(HttpStatus.BAD_REQUEST, "Paramètres invalides.", request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> internalError(Exception ex, HttpServletRequest request) {
        log.error("api_request_failed path={} error={}", request.getRequestURI(), ex.getMessage(), ex);
        return response(HttpStatus.INTERNAL_SERVER_ERROR, "Une erreur interne est survenue.", request);
    }

    private ResponseEntity<ApiError> response(HttpStatus status, String message, HttpServletRequest request) {
        return ResponseEntity.status(status).body(new ApiError(Instant.now(), status.value(), message, request.getRequestURI()));
    }
}
