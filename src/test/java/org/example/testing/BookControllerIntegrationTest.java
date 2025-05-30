package org.example.testing;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.testing.dto.BookRequest;
import org.example.testing.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerIntegrationTest extends AbstractTestcontainersTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository repository;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void createAndGetBookTest() throws Exception {
        String title = "Integration Test 1";
        String author = "Author";
        int year = 2005;

        BookRequest book = new BookRequest(title, author, year);

        ResultActions actions = mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(book)))
                .andExpect(status().isCreated());

        String location = actions.andReturn().getResponse().getHeader("Location");
        assertNotNull(location, "Location header должен быть возвращён");

        mockMvc.perform(get(location))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(title));
    }


    @Test
    void changeAndGetBookTest() throws Exception {
        String title = "Integration Test 2";
        String author = "Author";
        int year = 2005;

        BookRequest book = new BookRequest(title, author, year);

        ResultActions creation = mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(book)))
                .andExpect(status().isCreated());

        String location = creation.andReturn().getResponse().getHeader("Location");
        assertNotNull(location);

        Long id = extractIdFromLocation(location);

        String changedTitle = "Changed title";
        BookRequest сhangedBook = new BookRequest(changedTitle, author, year);

        mockMvc.perform(put("/books/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(сhangedBook)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(changedTitle));

        mockMvc.perform(get("/books/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(changedTitle));

    }


    @Test
    void deleteBookTest() throws Exception {
        String title = "Integration Test 3";
        String author = "Author";
        int year = 2005;

        BookRequest book = new BookRequest(title, author, year);

        ResultActions creation = mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(book)))
                .andExpect(status().isCreated());

        String location = creation.andReturn().getResponse().getHeader("Location");
        assertNotNull(location);

        Long id = extractIdFromLocation(location);

        mockMvc.perform(delete("/books/{id}", id))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/books/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("not found")));
    }

    @Test
    void getBookException() throws Exception {
        mockMvc.perform(get("/books/{id}", "99999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Book not found")));
    }


    @Test
    void postBookException() throws Exception {
        String author = "Author";
        int year = 2005;

        BookRequest book = new BookRequest(null, author, year);

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(book)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Title is required")));
    }

    @Test
    void putBookException() throws Exception {
        String title = "Integration Test 123";
        String author = "Author";
        int year = 2005;

        BookRequest book = new BookRequest(title, author, year);

        mockMvc.perform(put("/books/{id}", "999999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(book)))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Book not found")));
    }

    private Long extractIdFromLocation(String location) {
        String[] parts = location.split("/");
        return Long.parseLong(parts[parts.length - 1]);
    }
}


