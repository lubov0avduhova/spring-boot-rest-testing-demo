package org.example.testing;

import org.example.testing.entity.Book;
import org.example.testing.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookRepositoryTest extends AbstractTestcontainersTest{
    @Autowired
    private BookRepository bookRepository;

    @Test
    public void saveTest() {
        Book created = new Book(null, "title", "Author", 2000);

        Book saved = bookRepository.save(created);

        assertNotNull(saved.getId());

        assertEquals(created.getTitle(), saved.getTitle());
    }

    @Test
    public void findById() {
        Book created = new Book(null, "title", "Author", 2000);

        Book saved = bookRepository.save(created);

        Optional<Book> found = bookRepository.findById(saved.getId());

        assertTrue(found.isPresent());
    }

    @Test
    public void findByTitle() {
        Book created = new Book(null, "Spring title", "Author", 2000);

        Book saved = bookRepository.save(created);

        Optional<Book> found = bookRepository.findByTitle(saved.getTitle());

        assertTrue(found.isPresent());

        assertEquals(saved.getTitle(), found.get().getTitle());
    }

    @Test
    public void deleteBook() {
        Book created = new Book(null, "Spring title", "Author", 2000);

        Book saved = bookRepository.save(created);

        bookRepository.delete(saved);

        assertFalse(bookRepository.findById(saved.getId()).isPresent());
    }

}