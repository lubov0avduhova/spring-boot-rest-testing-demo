package org.example.testing;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.testing.controller.BookController;
import org.example.testing.dto.BookRequest;
import org.example.testing.dto.BookResponse;
import org.example.testing.exception.BookNotFoundException;
import org.example.testing.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(BookController.class)
public class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookService service;

    @Test
    public void getBookByIdTest() throws Exception {
        Long testId = 1L;
        BookResponse book = new BookResponse(testId, "Title", "Author", 1900);

        when(service.getBookById(testId)).thenReturn(book);

        mockMvc.perform(get("/books/{id}", testId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Title"));
    }

    @Test
    public void getBookByUnExistTest() throws Exception {
        long unexistId = -1L;
        when(service.getBookById(unexistId)).thenThrow(new BookNotFoundException(unexistId));

        mockMvc.perform(get("/books/" + unexistId))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Book not found: " + unexistId)));
    }

    @Test
    void shouldCreateBook() throws Exception {
        BookRequest request = new BookRequest("Spring Pro", "Author", 2000);
        BookResponse response = new BookResponse(1L, "Spring Pro", "Author", 2000);

        when(service.save(any(BookRequest.class))).thenReturn(response);

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/books/1"));
    }

    @Test
    void shouldReturn400IfTitleMissing() throws Exception {
        String invalidJson = "{\"author\" : \"Author\"," +
                "\"year\": 2000}";

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].field").value("title"))
                .andExpect(jsonPath("$.errors[0].message").value("Title is required"));

    }

    @Test
    void shouldReturn400IfAuthorMissing() throws Exception {
        String invalidJson = "{\"title\" : \"Title\"," +
                "\"year\": 2000}";

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].field").value("author"))
                .andExpect(jsonPath("$.errors[0].message").value("Author is required"));
    }

    @Test
    void shouldReturn400IfYearMissingOrLess() throws Exception {
        String invalidJson = "{\"title\" : \"Title\"," +
                "\"author\" : \"Author\"}";

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].field").value("year"))
                .andExpect(jsonPath("$.errors[0].message").value("Year is required"));


        String invalidJsonLessYear = "{\"title\" : \"Title\"," +
                "\"author\" : \"Author\"," +
                "\"year\": 1800}";

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJsonLessYear))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].field").value("year"))
                .andExpect(jsonPath("$.errors[0].message").value("Year must be greater than 1900"));
    }
}
