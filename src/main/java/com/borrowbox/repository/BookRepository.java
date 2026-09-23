package com.borrowbox.repository;

import com.borrowbox.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {
    Optional<Book> findByIsbn(String isbn);
    List<Book> findByAuthorContainingIgnoreCase(String author);
    List<Book> findByCategoryIgnoreCase(String category);
    boolean existsByIsbn(String isbn);
}