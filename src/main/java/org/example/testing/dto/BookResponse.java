package org.example.testing.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Ответ с данными книги")
public record BookResponse(

        @Schema(description = "Идентификатор книги", example = "1")
        Long id,

        @Schema(description = "Название книги", example = "Clean Code")
        String title,

        @Schema(description = "Автор книги", example = "Robert C. Martin")
        String author,

        @Schema(description = "Год публикации", example = "2008")
        Integer year
) {}


