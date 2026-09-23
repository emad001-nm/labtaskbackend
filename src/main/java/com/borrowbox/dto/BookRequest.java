package com.borrowbox.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class BookRequest {

    @NotBlank(message = "Title is required")
    @Size(min = 1, max = 200, message = "Title must be between 1 and 200 characters")
    private String title;

    @NotBlank(message = "Author is required")
    @Size(min = 2, max = 100, message = "Author must be between 2 and 100 characters")
    private String author;

    @NotBlank(message = "ISBN is required")
    @Pattern(regexp = "^(?:\\d{9}[\\dX]|\\d{13})$", message = "Invalid ISBN format")
    private String isbn;

    @NotBlank(message = "Category is required")
    private String category;

    @NotNull(message = "Total copies is required")
    @Min(value = 1, message = "Total copies must be at least 1")
    @Max(value = 1000, message = "Total copies cannot exceed 1000")
    private Integer totalCopies;

    @NotNull(message = "Available copies is required")
    @Min(value = 0, message = "Available copies cannot be negative")
    private Integer availableCopies;

    private Boolean borrowed;
}