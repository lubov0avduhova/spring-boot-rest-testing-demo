package org.example.testing.exception;

import lombok.Getter;

@Getter
public class BookNotFoundException extends RuntimeException {
    private Long id;

    public BookNotFoundException(Long id) {
        super("Book not found: " + id);
        this.id = id;
    }
}
