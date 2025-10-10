package dev.localcoder.springbooklibrary.dto.book;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookPopularityResponse {
    private Long bookId;
    private String title;
    private Long rentCount;
}
