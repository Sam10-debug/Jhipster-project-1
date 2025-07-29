package com.emorinken.bookapp.repository;

import com.emorinken.bookapp.domain.Book;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the Book entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    List<Book> findBooksByAuthor(String author);
    Optional<Book> findBookByIsbn(String isbn);
}
