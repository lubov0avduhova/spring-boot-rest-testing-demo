package org.example.testing;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        Book book = service.getBookById(id);

        return ResponseEntity.ok(new BookDTO(book.getId(), book.getTitle()));
    }

    @PostMapping
    public ResponseEntity<Void> createBook(@Valid @RequestBody BookDTO bookDTO) {
        BookDTO savedBookDTO = service.save(bookDTO);
        URI location = URI.create("/books/" + savedBookDTO.getId());
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> changeBook(@Valid @PathVariable Long id, @RequestBody BookDTO bookDTO) {
        BookDTO updatedBook = service.updateBook(id, bookDTO);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        service.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
