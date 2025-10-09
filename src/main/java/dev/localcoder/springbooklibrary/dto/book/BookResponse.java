package dev.localcoder.springbooklibrary.dto.book;

import lombok.Data;

@Data
public class BookResponse {
    private Long id;
    private String title;
    private String author;
    private int year;
    private String genre;
    private boolean isAvailable;
}
