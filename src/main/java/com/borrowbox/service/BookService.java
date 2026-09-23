package com.borrowbox.service;

import com.borrowbox.dto.BookRequest;
import com.borrowbox.model.Book;
import com.borrowbox.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public Book createBook(BookRequest request) {
        if (bookRepository.existsByIsbn(request.getIsbn())) {
            throw new IllegalArgumentException("Book with ISBN " + request.getIsbn() + " already exists");
        }

        Book book = Book.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .isbn(request.getIsbn())
                .category(request.getCategory())
                .totalCopies(request.getTotalCopies())
                .availableCopies(request.getAvailableCopies())
                .borrowed(request.getBorrowed() != null ? request.getBorrowed() : false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        log.info("Creating book: {}", book.getTitle());
        return bookRepository.save(book);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(String id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
    }

    public Book updateBook(String id, BookRequest request) {
        Book book = getBookById(id);

        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setIsbn(request.getIsbn());
        book.setCategory(request.getCategory());
        book.setTotalCopies(request.getTotalCopies());
        book.setAvailableCopies(request.getAvailableCopies());
        if (request.getBorrowed() != null) {
            book.setBorrowed(request.getBorrowed());
        }
        book.setUpdatedAt(LocalDateTime.now());

        log.info("Updating book: {}", id);
        return bookRepository.save(book);
    }

    public void deleteBook(String id) {
        Book book = getBookById(id);
        log.info("Deleting book: {}", book.getTitle());
        bookRepository.delete(book);
    }

    public Book borrowBook(String id) {
        Book book = getBookById(id);
        if (book.getAvailableCopies() <= 0) {
            throw new IllegalStateException("No copies available to borrow");
        }
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        book.setBorrowed(true);
        book.setUpdatedAt(LocalDateTime.now());
        return bookRepository.save(book);
    }

    public Book returnBook(String id) {
        Book book = getBookById(id);
        if (book.getAvailableCopies() >= book.getTotalCopies()) {
            throw new IllegalStateException("All copies already returned");
        }
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        if (book.getAvailableCopies().equals(book.getTotalCopies())) {
            book.setBorrowed(false);
        }
        book.setUpdatedAt(LocalDateTime.now());
        return bookRepository.save(book);
    }
}