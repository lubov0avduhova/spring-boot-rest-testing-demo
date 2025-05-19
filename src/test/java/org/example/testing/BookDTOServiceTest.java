package org.example.testing;

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
public class BookDTOServiceTest {

    @Mock
    private BookRepository repository;

    @InjectMocks
    private BookService service;

    @Test
    public void getBookByIdTest(){
        BookDTO expected = new BookDTO(1L, "title");
        Book entity = new Book(1L, "title");
        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        BookDTO actual = service.getBookById(1L);

        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowWhenBookNotFound() {
        when(repository.findById(-1L)).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(BookNotFoundException.class, () -> service.getBookById(-1L));
        assertEquals("Book not found: -1", thrown.getMessage());

    }
}
