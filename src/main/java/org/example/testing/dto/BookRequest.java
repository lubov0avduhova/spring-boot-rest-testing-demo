package org.example.testing.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Запрос на создание или обновление книги")
public record BookRequest(

        @NotBlank(message = "Title is required")
        @Schema(description = "Название книги", example = "Clean Code")
        String title,

        @NotBlank(message = "Author is required")
        @Schema(description = "Автор книги", example = "Robert C. Martin")
        String author,

        @NotNull(message = "Year is required")
        @Min(value = 1900, message = "Year must be greater than 1900")
        @Schema(description = "Год публикации", example = "2008")
        Integer year
) {
}


