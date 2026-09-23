package com.borrowbox.controller;

import com.borrowbox.dto.ApiResponse;
import com.borrowbox.dto.BookRequest;
import com.borrowbox.model.Book;
import com.borrowbox.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:5500", "http://127.0.0.1:5500", "http://localhost:3000"})
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<ApiResponse<Book>> createBook(@Valid @RequestBody BookRequest request) {
        Book book = bookService.createBook(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<Book>builder()
                        .success(true)
                        .message("Book created successfully")
                        .data(book)
                        .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Book>>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return ResponseEntity.ok(ApiResponse.<List<Book>>builder()
                .success(true)
                .message("Books fetched successfully")
                .data(books)
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Book>> getBookById(@PathVariable String id) {
        Book book = bookService.getBookById(id);
        return ResponseEntity.ok(ApiResponse.<Book>builder()
                .success(true)
                .message("Book fetched successfully")
                .data(book)
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Book>> updateBook(
            @PathVariable String id,
            @Valid @RequestBody BookRequest request) {
        Book book = bookService.updateBook(id, request);
        return ResponseEntity.ok(ApiResponse.<Book>builder()
                .success(true)
                .message("Book updated successfully")
                .data(book)
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBook(@PathVariable String id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .success(true)
                .message("Book deleted successfully")
                .build());
    }

    @PostMapping("/{id}/borrow")
    public ResponseEntity<ApiResponse<Book>> borrowBook(@PathVariable String id) {
        Book book = bookService.borrowBook(id);
        return ResponseEntity.ok(ApiResponse.<Book>builder()
                .success(true)
                .message("Book borrowed successfully")
                .data(book)
                .build());
    }

    @PostMapping("/{id}/return")
    public ResponseEntity<ApiResponse<Book>> returnBook(@PathVariable String id) {
        Book book = bookService.returnBook(id);
        return ResponseEntity.ok(ApiResponse.<Book>builder()
                .success(true)
                .message("Book returned successfully")
                .data(book)
                .build());
    }
}