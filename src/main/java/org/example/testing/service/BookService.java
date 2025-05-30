package org.example.testing.service;

import jakarta.validation.Valid;
import org.example.testing.exception.BookNotFoundException;
import org.example.testing.repository.BookRepository;
import org.example.testing.dto.BookRequest;
import org.example.testing.dto.BookResponse;
import org.example.testing.entity.Book;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public BookResponse getBookById(Long id) {
        Book foundBook = repository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

        return new BookResponse(foundBook.getId(), foundBook.getTitle(), foundBook.getAuthor(), foundBook.getYear());
    }

    public BookResponse save(BookRequest bookDTO) {
        Book book = new Book(null, bookDTO.title(), bookDTO.author(), bookDTO.year());
        Book saved = repository.save(book);

        return new BookResponse(saved.getId(), saved.getTitle(), saved.getAuthor(), saved.getYear());
    }

    public BookResponse updateBook(Long bookId, BookRequest bookDTO){
        Book foundBook = repository.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));

        foundBook.setTitle(bookDTO.title());

        repository.save(foundBook);

        return new BookResponse(foundBook.getId(), foundBook.getTitle(), foundBook.getAuthor(), foundBook.getYear());
    }

    public void deleteBook(@Valid Long id) {
        repository.deleteById(id);
    }
}
