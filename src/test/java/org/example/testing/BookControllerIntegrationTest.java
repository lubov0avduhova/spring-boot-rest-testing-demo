package org.example.testing;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository repository;

    @Test
    void createAndGetBookTest() throws Exception {
        ResultActions actions = mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Integration Test\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/books/1"));

        Optional<Book> foundBook = repository.findByTitle("Integration Test");

        assertNotNull(foundBook.get());

        String location = actions.andReturn().getResponse().getHeader("Location");

        mockMvc.perform(get(location))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Integration Test"));

    }


    @Test
    void changeAndGetBookTest() throws Exception {
        String title = "Integration Test";
        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"" + title + "\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/books/1"));


        Optional<Book> foundBook = repository.findByTitle(title);

        assertNotNull(foundBook.get());

        String changeTitle = "Changed title";

        mockMvc.perform(put("/books/{id}", foundBook.get().getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": " + foundBook.get().getId() + "," +
                                "\"title\": \"" + changeTitle + "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(changeTitle));

        mockMvc.perform(get("/books" + "/" + foundBook.get().getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(changeTitle));

    }


    @Test
    void deleteBookTest() throws Exception {
        ResultActions actions = mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Integration Test\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/books/1"));

        Optional<Book> foundBook = repository.findByTitle("Integration Test");

        assertNotNull(foundBook.get());

        mockMvc.perform(delete("/books/{id}", foundBook.get().getId()))
                .andExpect(status().isOk());


        String location = actions.andReturn().getResponse().getHeader("Location");

        mockMvc.perform(get(location))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("not found")));
        ;

    }

    @Test
    void getBookException() throws Exception {
        mockMvc.perform(get("/books/{id}", "99999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Book not found")));
    }


    @Test
    void postBookException() throws Exception {
        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Title is required")));
    }

    @Test
    void putBookException() throws Exception {
        mockMvc.perform(put("/books/{id}", "999999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"doesn't matter\"}"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Book not found")));
    }
}


