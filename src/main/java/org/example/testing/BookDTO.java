package org.example.testing;

import jakarta.validation.constraints.NotBlank;

public class BookDTO {
    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    public BookDTO(Long id, String title) {
        this.id = id;
        this.title = title;
    }


    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
