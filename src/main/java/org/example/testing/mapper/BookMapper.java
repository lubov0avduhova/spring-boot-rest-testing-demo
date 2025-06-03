package org.example.testing.mapper;

import org.example.testing.dto.BookRequest;
import org.example.testing.dto.BookResponse;
import org.example.testing.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BookMapper {
    Book toEntity(BookRequest request);

    BookResponse toResponse(Book entity);

    void updateBookFromRequest(BookRequest request, @MappingTarget Book book);
}