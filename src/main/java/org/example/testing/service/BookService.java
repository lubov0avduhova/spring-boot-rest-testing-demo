package org.example.testing.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.testing.mapper.BookMapper;
import org.example.testing.exception.BookNotFoundException;
import org.example.testing.repository.BookRepository;
import org.example.testing.dto.BookRequest;
import org.example.testing.dto.BookResponse;
import org.example.testing.entity.Book;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository repository;
    private final BookMapper bookMapper;

    public BookResponse getBookById(Long id) {
        Book foundBook = repository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

        return bookMapper.toResponse(foundBook);
    }

    public BookResponse save(BookRequest bookRequest) {
        Book book = bookMapper.toEntity(bookRequest);
        Book saved = repository.save(book);
        return bookMapper.toResponse(saved);
    }

    public BookResponse updateBook(Long bookId, BookRequest bookRequest) {
        Book foundBook = repository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));

        bookMapper.updateBookFromRequest(bookRequest, foundBook);

        Book updated = repository.save(foundBook);
        return bookMapper.toResponse(updated);
    }

    public void deleteBook(@Valid Long id) {
        repository.deleteById(id);
    }
}
