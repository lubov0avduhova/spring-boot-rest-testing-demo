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
public class BookControllerIntegrationTest extends AbstractTestcontainersTest{

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository repository;

    @Test
    void createAndGetBookTest() throws Exception {
        String title = "Integration Test 1";

        ResultActions actions = mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"" + title + "\"}"))
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

        ResultActions creation = mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"" + title + "\"}"))
                .andExpect(status().isCreated());

        String location = creation.andReturn().getResponse().getHeader("Location");
        assertNotNull(location);

        Long id = extractIdFromLocation(location);

        String changedTitle = "Changed title";

        mockMvc.perform(put("/books/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": " + id + ", \"title\": \"" + changedTitle + "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(changedTitle));

        mockMvc.perform(get("/books/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(changedTitle));

    }


    @Test
    void deleteBookTest() throws Exception {
        String title = "Integration Test 3";

        ResultActions creation = mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"" + title + "\"}"))
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

    private Long extractIdFromLocation(String location) {
        String[] parts = location.split("/");
        return Long.parseLong(parts[parts.length - 1]);
    }
}


