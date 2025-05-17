package org.example.testing;

public class BookNotFoundException extends RuntimeException {
    private Long id;

    public BookNotFoundException(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
