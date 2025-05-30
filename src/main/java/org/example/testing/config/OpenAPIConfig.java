package org.example.testing.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI bookApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Book CRUD API")
                        .description("API для управления книгами")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Lubov Avduhova")
                                .url("https://github.com/lubov0avduhova")));
    }
}

