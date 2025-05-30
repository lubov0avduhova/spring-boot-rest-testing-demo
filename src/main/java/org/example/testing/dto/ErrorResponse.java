package org.example.testing.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Структура ошибки")
public record ErrorResponse(
        @Schema(description = "Сообщение об ошибке", example = "Книга с ID 1 не найдена")
        String message,

        @Schema(description = "Дата и время", example = "2025-05-28T14:55:01")
        LocalDateTime timestamp
) {}
