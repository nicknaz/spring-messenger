package com.valar.messenger.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler(DialogExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> objectNotFoundedException(final DialogExistException e) {
        log.debug("Получен статус 400 Bad Request {}", e.getMessage(), e);
        return Map.of("exception", e.getMessage());
    }

    @ExceptionHandler(com.valar.messenger.exception.NotFoundedException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> objectNotFoundedException(final NotFoundedException e) {
        log.debug("Получен статус 404 Not Founded {}", e.getMessage(), e);
        return Map.of("exception", e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Map<String, String> objectNotFoundedException(final AccessDeniedException e) {
        log.debug("Получен статус 403 Forbidden {}", e.getMessage(), e);
        return Map.of("exception", e.getMessage());
    }

}
