package ru.aasmc.client.errorhandling;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class DefaultControllerAdvice {

    @ExceptionHandler(ClientException.class)
    public ResponseEntity<ApiError> handleClientException(final ClientException ex) {
        log.error("Received ClientException. Message: {}, Code: {}", ex.getMessage(), ex.getCode());
        ApiError result = ApiError.builder()
                .message(ex.getMessage())
                .code(ex.getCode())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(result, HttpStatus.valueOf(ex.getCode()));
    }

}
