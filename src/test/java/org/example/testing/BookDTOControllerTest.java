package org.example.testing;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(BookController.class)
public class BookDTOControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookService service;

   @Test
    public void getBookByIdTest() throws Exception {
        Book book = new Book(1L, "Title");

        when(service.getBookById(1L)).thenReturn(book);

        mockMvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Title"));


    }

    @Test
    public void getBookByUnExistTest() throws Exception {
        long unexistId = -1L;
        when(service.getBookById(unexistId)).thenThrow(new BookNotFoundException(unexistId));

        mockMvc.perform(get("/books/" + unexistId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Book not found:" + unexistId));
    }

    @Test
    void shouldCreateBook() throws Exception {
        BookDTO bookDTOToCreate = new BookDTO(null, "Spring Pro");
        BookDTO savedBookDTO = new BookDTO(1L, "Spring Pro");

        when(service.save(any(BookDTO.class))).thenReturn(savedBookDTO);

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDTOToCreate)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/books/1"));
    }

    @Test
    void shouldReturn400IfTitleMissing() throws Exception {
        String invalidJson = "{}";

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].field").value("title"))
                .andExpect(jsonPath("$.errors[0].message").value("Title is required"));

    }
}
