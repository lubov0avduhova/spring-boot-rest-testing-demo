package org.example.testing;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public Book getBookById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    public BookDTO save(BookDTO bookDTO) {
        Book book = new Book(null, bookDTO.getTitle());
        Book saved = repository.save(book);

        return new BookDTO(saved.getId(), saved.getTitle());
    }

    public BookDTO updateBook(Long bookId, BookDTO bookDTO){
        Book found = repository.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));

        found.setTitle(bookDTO.getTitle());

        repository.save(found);

        return new BookDTO(found.getId(), found.getTitle());
    }

    public void deleteBook(@Valid Long id) {
        repository.deleteById(id);
    }
}
