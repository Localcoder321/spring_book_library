package dev.localcoder.springbooklibrary.dto.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class UpdateBookRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String author;
    @PositiveOrZero
    private int year;
    @NotBlank
    private String genre;
}
