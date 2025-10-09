package dev.localcoder.springbooklibrary.dto.rental;

import lombok.Data;

import java.time.Instant;

@Data
public class RentalResponse {
    private Long id;
    private Long bookId;
    private String bookTitle;
    private Long readerId;
    private String readerName;
    private Instant takenOn;
    private Instant dueOn;
    private Instant returnedOn;
}
