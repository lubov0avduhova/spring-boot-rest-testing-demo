package org.example.testing;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Book save(Book book);

    Optional<Book> findByTitle(String title);

}
