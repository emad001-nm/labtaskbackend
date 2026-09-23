package com.borrowbox.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "books")
public class Book {

    @Id
    private String id;

    private String title;
    private String author;
    private String isbn;
    private String category;
    private Integer totalCopies;
    private Integer availableCopies;
    private Boolean borrowed;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}