package org.example.testing;

import org.example.testing.dto.BookResponse;
import org.example.testing.entity.Book;
import org.example.testing.exception.BookNotFoundException;
import org.example.testing.repository.BookRepository;
import org.example.testing.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository repository;

    @InjectMocks
    private BookService service;

    @Test
    public void getBookByIdTest(){
        BookResponse expected = new BookResponse(1L, "title","Author", 2000);
        Book entity = new Book(1L, "title", "Author", 2000);
        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        BookResponse actual = service.getBookById(1L);

        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowWhenBookNotFound() {
        when(repository.findById(-1L)).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(BookNotFoundException.class, () -> service.getBookById(-1L));
        assertEquals("Book not found: -1", thrown.getMessage());

    }
}
