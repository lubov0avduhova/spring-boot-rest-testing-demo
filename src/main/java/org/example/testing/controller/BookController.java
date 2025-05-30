package org.example.testing.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.testing.dto.BookRequest;
import org.example.testing.dto.BookResponse;
import org.example.testing.dto.ErrorResponse;
import org.example.testing.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
@Tag(name = "Книги", description = "Операции для управления книгами")
public class BookController {

    private final BookService service;

    @Operation(summary = "Получить книгу по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Книга найдена"),
            @ApiResponse(responseCode = "404", description = "Книга не найдена",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBookById(
            @Parameter(description = "ID книги", example = "1")
            @PathVariable Long id) {
        BookResponse book = service.getBookById(id);
        return ResponseEntity.ok(book);
    }

    @Operation(summary = "Создать новую книгу")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Книга успешно создана"),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<Void> createBook(
            @Parameter(description = "Данные новой книги")
            @Valid @RequestBody BookRequest bookDTO) {
        BookResponse saved = service.save(bookDTO);
        URI location = URI.create("/books/" + saved.id());
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Обновить книгу по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Книга успешно обновлена"),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Книга не найдена",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> changeBook(
            @Parameter(description = "ID книги", example = "1")
            @Valid @PathVariable Long id,
            @RequestBody BookRequest bookDTO) {
        BookResponse updated = service.updateBook(id, bookDTO);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Удалить книгу по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Книга удалена"),
            @ApiResponse(responseCode = "404", description = "Книга не найдена",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(
            @Parameter(description = "ID книги", example = "1")
            @PathVariable Long id) {
        service.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
