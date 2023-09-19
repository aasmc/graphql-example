package ru.aasmc.client.errorhandling;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@RequiredArgsConstructor
public class ApiError {
    private final int code;
    private final String message;
    private final LocalDateTime timestamp;
}
